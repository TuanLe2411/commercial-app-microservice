package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions;

import com.commercial.app.es_event_sync_service.mongo_repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteHandler {
    final ProductRepository productRepository;

    public ProductDeleteHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handle(String productId) {
        this.productRepository.deleteProductById(productId);
    }
}
