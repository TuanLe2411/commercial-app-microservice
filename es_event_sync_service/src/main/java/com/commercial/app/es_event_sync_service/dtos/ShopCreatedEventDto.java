package com.commercial.app.es_event_sync_service.dtos;

import lombok.Data;

@Data
public class ShopCreatedEventDto {
    private String name;
    private String ownerId;
}
