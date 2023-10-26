package com.commercial.app.es_event_sync_service.special_functions;

import com.commercial.app.es_event_sync_service.es.events.Event;
import com.commercial.app.es_event_sync_service.mongo_models.EsEventHistory;
import com.commercial.app.es_event_sync_service.mongo_repositories.EsEventRepository;
import com.commercial.app.es_event_sync_service.special_functions.es_event_functions.EsEventFunctionsRouter;
import com.commercial.app.es_event_sync_service.utils.JsonHelper;
import com.commercial.app.es_event_sync_service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageHandler {
    @Autowired
    EsEventRepository esEventRepository;

    @Autowired
    EsEventFunctionsRouter esEventFunctionsRouter;

    @KafkaListener(topics = "es_event_stream", groupId = "event-sync")
    public void handleEsEvent(@Payload String event) {
        Event esEvent = (Event) JsonHelper.toObject(event, Event.class);
        System.out.println(esEvent.toString());
        EsEventHistory esEventHistory = this.esEventRepository.findOneById(esEvent.getId());
        if(isExecutedOkEsEventHistory(esEventHistory)) {
            System.out.println("es event is handled with id: " + esEvent.getId());
        }
        try {
            this.esEventFunctionsRouter.route(esEvent);
            this.esEventRepository.insert(EsEventHistory.builder()
                .id(esEvent.getId())
                .status(Constants.ES_EVENT_STATUS.HANDLED_OK)
                .build());
        } catch (RuntimeException ex) {
            this.esEventRepository.insert(EsEventHistory.builder()
                .id(esEvent.getId())
                .status(Constants.ES_EVENT_STATUS.HANDLED_ERROR)
                .build());
        }
    }

    private boolean isExecutedOkEsEventHistory(EsEventHistory esEventHistory) {
        return esEventHistory != null
            && esEventHistory.getStatus().equals(Constants.ES_EVENT_STATUS.HANDLED_OK);
    }
}
