package com.commercial.app.product_service.events.publishers;

import com.commercial.app.product_service.es.events.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnProductEsEventPublisher extends ApplicationEvent {
    private final Event productEsEvent;
    public OnProductEsEventPublisher(Object source, Event productEsEvent) {
        super(source);
        this.productEsEvent = productEsEvent;
    }
}
