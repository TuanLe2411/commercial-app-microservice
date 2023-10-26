package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions;

import com.commercial.app.es_event_sync_service.dtos.ProductCreatedEventDto;
import com.commercial.app.es_event_sync_service.mongo_models.Product;
import com.commercial.app.es_event_sync_service.mongo_repositories.ProductRepository;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedHandler {
    final ProductRepository productRepository;

    public ProductCreatedHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handle(String productId, String payload) {
        ProductCreatedEventDto productCreatedEventDto = (ProductCreatedEventDto) JsonHelper.toObject(
            payload,
            ProductCreatedEventDto.class
        );
        this.productRepository.insert(Product.builder()
                .id(productId)
                .category(productCreatedEventDto.getCategory())
                .name(productCreatedEventDto.getName())
                .price(productCreatedEventDto.getPrice())
                .amount(productCreatedEventDto.getAmount())
            .build());
    }
}
