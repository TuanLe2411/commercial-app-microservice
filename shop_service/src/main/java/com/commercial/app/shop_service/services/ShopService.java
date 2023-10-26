package com.commercial.app.shop_service.services;

import com.commercial.app.shop_service.es.commands.CreateNewShopCommand;
import com.commercial.app.shop_service.es.commands.DeleteShopCommand;
import com.commercial.app.shop_service.es.commands.UpdateShopInfoCommand;
import com.commercial.app.shop_service.es.events.Event;
import com.commercial.app.shop_service.es.events.ShopCreated;
import com.commercial.app.shop_service.es.events.ShopRemoved;
import com.commercial.app.shop_service.es.events.ShopUpdated;
import com.commercial.app.shop_service.es.repository.EventRepository;
import com.commercial.app.shop_service.events.publishers.OnShopEsEventPublisher;
import com.commercial.app.shop_service.exceptions.ShopUnavailableToChangeException;
import com.commercial.app.shop_service.exceptions.ShopUnavailableToCreateException;
import com.commercial.app.shop_service.utils.Constants;
import com.commercial.app.shop_service.utils.EventValidator;
import com.commercial.app.shop_service.utils.JsonHelper;
import com.commercial.app.shop_service.utils.ValueRandomGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private final EventRepository eventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ShopService(EventRepository eventRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.eventRepository = eventRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(CreateNewShopCommand createNewShopCommand) throws JsonProcessingException {
        if (this.isForbiddenToCreateNewShop(createNewShopCommand)) {
            return;
        }
        String shopId = ValueRandomGenerator.getRandomShopId();
        Event shopCreated = ShopCreated.builder()
            .version(1)
            .stream(Constants.ES.EVENT_STREAM.SHOP)
            .streamId(shopId)
            .type(Constants.ES.EVENT_TYPE.SHOP_CREATED)
            .payload(JsonHelper.toString(createNewShopCommand))
            .ownerId(createNewShopCommand.getOwnerId())
            .build();
        this.eventRepository.save(shopCreated);
        this.applicationEventPublisher.publishEvent(new OnShopEsEventPublisher(this, shopCreated));
    }

    public void execute(UpdateShopInfoCommand updateShopCommand) throws JsonProcessingException {
        String shopId = updateShopCommand.getShopId();
        Event lastShopEvent = this.eventRepository.findTopByStreamIdOrderByVersionDesc(
            shopId
        );
        if (this.isForbiddenToChangeShopInfo(updateShopCommand.getOwnerId(), lastShopEvent)) {
            return;
        }
        Event shopUpdated = ShopUpdated.builder()
            .version(lastShopEvent.getVersion() + 1)
            .stream(Constants.ES.EVENT_STREAM.SHOP)
            .streamId(shopId)
            .type(Constants.ES.EVENT_TYPE.SHOP_UPDATED)
            .payload(JsonHelper.toString(updateShopCommand))
            .build();
        if (updateShopCommand.getNewOwnerId() == null) {
            shopUpdated.setOwnerId(updateShopCommand.getOwnerId());
        } else {
            shopUpdated.setOwnerId(updateShopCommand.getNewOwnerId());
        }
        this.eventRepository.save(shopUpdated);
        this.applicationEventPublisher.publishEvent(new OnShopEsEventPublisher(this, shopUpdated));
    }

    public void execute(DeleteShopCommand deleteShopCommand) {
        String shopId = deleteShopCommand.getShopId();
        Event lastShopEvent = this.eventRepository.findTopByStreamIdOrderByVersionDesc(
            shopId
        );
        if(this.isForbiddenToChangeShopInfo(deleteShopCommand.getOwnerId(), lastShopEvent)) {
            return;
        }
        Event shopRemoved = ShopRemoved.builder()
            .version(lastShopEvent.getVersion() + 1)
            .stream(Constants.ES.EVENT_STREAM.SHOP)
            .streamId(shopId)
            .ownerId(deleteShopCommand.getOwnerId())
            .type(Constants.ES.EVENT_TYPE.SHOP_REMOVED)
            .build();
        this.eventRepository.save(shopRemoved);
        this.applicationEventPublisher.publishEvent(new OnShopEsEventPublisher(this, shopRemoved));
    }

    private boolean isForbiddenToChangeShopInfo(String ownerId, Event lastShopEvent) {
        EventValidator eventValidator = EventValidator.builder()
            .event(lastShopEvent)
            .build();
        if (eventValidator.isShopUnCreated() || eventValidator.isShopRemoved()) {
            throw new ShopUnavailableToChangeException("Shop must be existed");
        }
        if (!eventValidator.isOwnerOfShop(ownerId)) {
            throw new ShopUnavailableToChangeException("Only owner can update shop info");
        }
        return false;
    }

    private boolean isForbiddenToCreateNewShop(CreateNewShopCommand createNewShopCommand) {
        Event event = this.eventRepository.findTopByOwnerIdAndStreamOrderByCreatedAtDesc(
            createNewShopCommand.getOwnerId(),
            Constants.ES.EVENT_STREAM.SHOP
        );
        EventValidator eventValidator = EventValidator.builder()
            .event(event)
            .build();
        if (eventValidator.isUserCreatedShop()) {
            throw new ShopUnavailableToCreateException("User can own one shop at the same time");
        }
        return false;
    }
}
