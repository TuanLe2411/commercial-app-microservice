package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.shop_functions;

import com.commercial.app.es_event_sync_service.dtos.ShopCreatedEventDto;
import com.commercial.app.es_event_sync_service.dtos.ShopUpdatedEventDto;
import com.commercial.app.es_event_sync_service.mongo_models.Shop;
import com.commercial.app.es_event_sync_service.mongo_repositories.ShopRepository;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import org.springframework.stereotype.Component;

@Component
public class ShopUpdatedHandler {
    final ShopRepository shopRepository;

    public ShopUpdatedHandler(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void handle(String shopId, String payload) {
        ShopUpdatedEventDto shopUpdatedEventDto = (ShopUpdatedEventDto) JsonHelper.toObject(
            payload,
            ShopUpdatedEventDto.class
        );
        Shop shop = this.shopRepository.findOneById(shopId);
        if(shopUpdatedEventDto.getName() != null) {
            shop.setName(shopUpdatedEventDto.getName());
        }
        if(shopUpdatedEventDto.getNewOwnerId() != null) {
            shop.setOwnerId(shopUpdatedEventDto.getNewOwnerId());
        }
        this.shopRepository.save(shop);
    }
}
