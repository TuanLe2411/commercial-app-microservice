package com.commercial.app.shop_service.events.listeners;

import com.commercial.app.shop_service.events.publishers.OnShopEsEventPublisher;
import com.commercial.app.shop_service.special_functions.KafkaSenderFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnShopEsEventListener implements ApplicationListener<OnShopEsEventPublisher> {
    @Value("${kafka.es-event-stream.topic}")
    private String esEventStreamTopic;

    private final KafkaSenderFunctions kafkaSenderFunctions;

    public OnShopEsEventListener(KafkaSenderFunctions kafkaSenderFunctions) {
        this.kafkaSenderFunctions = kafkaSenderFunctions;
    }

    @Override
    public void onApplicationEvent(OnShopEsEventPublisher event) {
        this.kafkaSenderFunctions.sendMessage(esEventStreamTopic, event.getShopEsEvent());

    }
}
