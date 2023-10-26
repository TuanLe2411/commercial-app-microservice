package com.commercial.app.product_service.services;

import com.commercial.app.product_service.es.commands.CreateNewProductCommand;
import com.commercial.app.product_service.es.commands.DeleteProductCommand;
import com.commercial.app.product_service.es.commands.UpdateProductCommand;
import com.commercial.app.product_service.es.events.Event;
import com.commercial.app.product_service.es.events.ProductCreated;
import com.commercial.app.product_service.es.events.ProductRemoved;
import com.commercial.app.product_service.es.events.ProductUpdated;
import com.commercial.app.product_service.es.repository.EventRepository;
import com.commercial.app.product_service.events.publishers.OnProductEsEventPublisher;
import com.commercial.app.product_service.exceptions.ProductUnavailableToChange;
import com.commercial.app.product_service.utils.Constants;
import com.commercial.app.product_service.utils.JsonHelper;
import com.commercial.app.product_service.utils.ValueRandomGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService {
    private final EventRepository eventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    public ProductService(EventRepository eventRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.eventRepository = eventRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(CreateNewProductCommand createNewProductCommand) throws JsonProcessingException {
        String productId = ValueRandomGenerator.getRandomProductId();
        Event productCreated = ProductCreated.builder()
            .version(1)
            .stream(Constants.ES.EVENT_STREAM.PRODUCT)
            .streamId(productId)
            .type(Constants.ES.EVENT_TYPE.PRODUCT_CREATED)
            .payload(JsonHelper.toString(createNewProductCommand))
            .build();
        this.eventRepository.save(productCreated);
        this.applicationEventPublisher.publishEvent(new OnProductEsEventPublisher(this, productCreated));
    }

    public void execute(UpdateProductCommand updateProductCommand) throws JsonProcessingException {
        String productId = updateProductCommand.getProductId();
        Event lastProductEvent = this.eventRepository.findTopByStreamIdOrderByVersionDesc(
            productId
        );
        if(this.isProductForbiddenToChange(lastProductEvent)) {
            throw new ProductUnavailableToChange("Product is uncreated or removed");
        }
        Event productUpdated = ProductUpdated.builder()
            .version(lastProductEvent.getVersion() + 1)
            .stream(Constants.ES.EVENT_STREAM.PRODUCT)
            .streamId(productId)
            .type(Constants.ES.EVENT_TYPE.PRODUCT_UPDATED)
            .payload(JsonHelper.toString(updateProductCommand))
            .build();
        this.eventRepository.save(productUpdated);
        this.applicationEventPublisher.publishEvent(new OnProductEsEventPublisher(this, productUpdated));
    }

    public void execute(DeleteProductCommand deleteProductCommand) {
        String productId = deleteProductCommand.getProductId();
        Event lastProductEvent = this.eventRepository.findTopByStreamIdOrderByVersionDesc(
            productId
        );
        if(this.isProductForbiddenToChange(lastProductEvent)) {
            throw new ProductUnavailableToChange("Product is uncreated or removed");
        }
        Event productRemoved = ProductRemoved.builder()
            .version(lastProductEvent.getVersion() + 1)
            .stream(Constants.ES.EVENT_STREAM.PRODUCT)
            .streamId(productId)
            .type(Constants.ES.EVENT_TYPE.PRODUCT_REMOVED)
            .build();
        this.eventRepository.save(productRemoved);
        this.applicationEventPublisher.publishEvent(new OnProductEsEventPublisher(this, productRemoved));
    }

    private boolean isProductForbiddenToChange(Event lastProductEvent) {
        return lastProductEvent == null || Objects.equals(lastProductEvent.getType(),
            Constants.ES.EVENT_TYPE.PRODUCT_REMOVED);
    }
}
