package com.commercial.app.es_event_sync_service.mongo_models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("shops")
@Data
@Builder
public class Shop {
    @MongoId
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String ownerId;
}
