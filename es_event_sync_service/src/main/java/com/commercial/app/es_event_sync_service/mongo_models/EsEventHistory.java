package com.commercial.app.es_event_sync_service.mongo_models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("es_event_histories")
@Data
@Builder
public class EsEventHistory {
    @Id
    private Long id;

    private String status;
}
