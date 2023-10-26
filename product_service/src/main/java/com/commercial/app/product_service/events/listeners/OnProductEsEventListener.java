package com.commercial.app.product_service.events.listeners;

import com.commercial.app.product_service.events.publishers.OnProductEsEventPublisher;
import com.commercial.app.product_service.special_functions.KafkaSenderFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnProductEsEventListener implements ApplicationListener<OnProductEsEventPublisher> {
    @Value("${kafka.es-event-stream.topic}")
    private String esEventStreamTopic;

    private final KafkaSenderFunctions kafkaSenderFunctions;

    public OnProductEsEventListener(KafkaSenderFunctions kafkaSenderFunctions) {
        this.kafkaSenderFunctions = kafkaSenderFunctions;
    }

    @Override
    public void onApplicationEvent(OnProductEsEventPublisher event) {
        this.kafkaSenderFunctions.sendMessage(esEventStreamTopic, event.getProductEsEvent());
    }
}
