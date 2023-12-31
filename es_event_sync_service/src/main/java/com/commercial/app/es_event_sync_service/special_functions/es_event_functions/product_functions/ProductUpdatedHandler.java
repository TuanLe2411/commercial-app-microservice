package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions;

import com.commercial.app.es_event_sync_service.dtos.ProductUpdatedEventDto;
import com.commercial.app.es_event_sync_service.mongo_models.Product;
import com.commercial.app.es_event_sync_service.mongo_repositories.ProductRepository;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdatedHandler {
    final ProductRepository productRepository;

    public ProductUpdatedHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handle(String productId, String payload) {
        ProductUpdatedEventDto productUpdatedEventDto = (ProductUpdatedEventDto) JsonHelper.toObject(
            payload,
            ProductUpdatedEventDto.class
        );
        Product currentProduct = this.productRepository.findOneById(productId);
        currentProduct.updateFromEvent(productUpdatedEventDto);
        this.productRepository.save(currentProduct);
    }
}
