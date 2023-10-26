package com.commercial.app.es_event_sync_service.dtos;

import lombok.Data;

@Data
public class ProductCreatedEventDto {
    private String name;
    private String category;
    private String price;
    private Integer amount;
    private String shopId;
}
