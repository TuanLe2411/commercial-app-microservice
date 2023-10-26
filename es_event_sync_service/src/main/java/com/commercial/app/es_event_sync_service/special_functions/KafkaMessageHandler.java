package com.commercial.app.es_event_sync_service.special_functions;

import com.commercial.app.es_event_sync_service.es.events.Event;
import com.commercial.app.es_event_sync_service.mongo_models.EsEventHistory;
import com.commercial.app.es_event_sync_service.mongo_repositories.EsEventRepository;
import com.commercial.app.es_event_sync_service.special_functions.es_event_functions.EsEventFunctionsRouter;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import com.commercial.app.es_event_sync_service.utils.Constants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageHandler {
    final EsEventRepository esEventRepository;

    final EsEventFunctionsRouter esEventFunctionsRouter;

    public KafkaMessageHandler(EsEventRepository esEventRepository, EsEventFunctionsRouter esEventFunctionsRouter) {
        this.esEventRepository = esEventRepository;
        this.esEventFunctionsRouter = esEventFunctionsRouter;
    }

    @KafkaListener(topics = "es_event_stream", groupId = "event-sync")
    public void handleEsEvent(@Payload String event) {
        Event esEvent = (Event) JsonHelper.toObject(event, Event.class);
        System.out.println(esEvent.toString());
        EsEventHistory esEventHistory = this.esEventRepository.findOneById(esEvent.getId());
        if(isExecutedOkEsEventHistory(esEventHistory)) {
            System.out.println("es event is handled with id: " + esEvent.getId());
        }
        EsEventHistory eventHistory = EsEventHistory.builder()
            .id(esEvent.getId())
            .payload(esEvent.getPayload())
            .stream(esEvent.getStream())
            .streamId(esEvent.getStreamId())
            .type(esEvent.getType())
            .build();
        try {
            this.esEventFunctionsRouter.route(esEvent);
            eventHistory.setStatus(Constants.ES_EVENT_STATUS.HANDLED_OK);
        } catch (RuntimeException ex) {
            eventHistory.setStatus(Constants.ES_EVENT_STATUS.HANDLED_ERROR);
        } finally {
            this.esEventRepository.insert(eventHistory);
        }
    }

    private boolean isExecutedOkEsEventHistory(EsEventHistory esEventHistory) {
        return esEventHistory != null
            && esEventHistory.getStatus().equals(Constants.ES_EVENT_STATUS.HANDLED_OK);
    }
}
