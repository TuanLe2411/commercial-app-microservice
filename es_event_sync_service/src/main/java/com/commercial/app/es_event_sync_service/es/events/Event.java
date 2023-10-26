package com.commercial.app.es_event_sync_service.es.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Event {
    private Long id;

    private String stream;

    private String streamId;

    private int version;

    private String type;

    private String payload;

    @Override
    public String toString() {
        return "id: " + id + " " + "stream: " + stream + " " + "streamId: " + streamId + " " + "version: " + version
            + " " + "type: " + type + " " + "payload: " + payload;
    }
}
