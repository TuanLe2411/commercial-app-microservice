package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.shop_functions;

import com.commercial.app.es_event_sync_service.mongo_repositories.ProductRepository;
import com.commercial.app.es_event_sync_service.mongo_repositories.ShopRepository;
import org.springframework.stereotype.Component;

@Component
public class ShopRemovedHandler {
    final ShopRepository shopRepository;

    public ShopRemovedHandler(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void handle(String shopId) {
        this.shopRepository.deleteById(shopId);
    }
}
