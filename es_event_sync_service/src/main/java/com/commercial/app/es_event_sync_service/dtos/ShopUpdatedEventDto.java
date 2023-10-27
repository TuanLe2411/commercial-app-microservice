package com.commercial.app.es_event_sync_service.dtos;

import lombok.Data;

@Data
public class ShopUpdatedEventDto {
    private String name;
    private String shopId;
    private String newOwnerId;
    private String ownerId;
}
