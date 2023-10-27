package com.commercial.app.es_event_sync_service.special_functions.es_event_functions.shop_functions;

import com.commercial.app.es_event_sync_service.dtos.ShopCreatedEventDto;
import com.commercial.app.es_event_sync_service.mongo_models.Shop;
import com.commercial.app.es_event_sync_service.mongo_repositories.ShopRepository;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@Validated
public class ShopCreatedHandler {
    final ShopRepository shopRepository;

    public ShopCreatedHandler(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void handle(@NotBlank String shopId, String payload) {
        ShopCreatedEventDto shopCreatedEventDto = (ShopCreatedEventDto) JsonHelper.toObject(
            payload,
            ShopCreatedEventDto.class
        );
        this.shopRepository.insert(Shop.builder()
            .id(shopId)
            .ownerId(shopCreatedEventDto.getOwnerId())
            .name(shopCreatedEventDto.getName())
            .build());
    }
}
