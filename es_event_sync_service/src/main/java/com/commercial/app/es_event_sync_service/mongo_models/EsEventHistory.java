package com.commercial.app.es_event_sync_service.mongo_models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("es_event_histories")
@Data
@Builder
public class EsEventHistory {
    @MongoId
    private Long id;

    private String status;

    private String stream;

    private String streamId;

    private String type;

    private String payload;
}
