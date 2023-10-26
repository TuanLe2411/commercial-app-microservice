package com.commercial.app.product_service.special_functions;

import com.commercial.app.product_service.es.events.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderFunctions {
    private final KafkaTemplate<String, Event> esEventTemplate;

    public KafkaSenderFunctions(KafkaTemplate<String, Event> esEventTemplate) {
        this.esEventTemplate = esEventTemplate;
    }

    public void sendMessage(String topic, Object message) {
        if (message.getClass().equals(Event.class)) {
            esEventTemplate.send(topic, (Event) message);
        }
    }
}
