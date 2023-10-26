package com.commercial.app.es_event_sync_service.mongo_models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("products")
@Data
@Builder
public class Product {
    @MongoId
    private String id;

    private String name;

    private String category;

    private String price;

    @Indexed(unique = true)
    private String shopId;

    private int amount;
}
