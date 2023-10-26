package com.commercial.app.es_event_sync_service.dtos;

import lombok.Data;

@Data
public class ProductUpdatedEventDto {
    private String name;
    private String category;
    private String price;
    private String shopId;
    private Integer amount;
}
