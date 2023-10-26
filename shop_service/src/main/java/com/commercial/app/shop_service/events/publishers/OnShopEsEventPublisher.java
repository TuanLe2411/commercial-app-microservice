package com.commercial.app.shop_service.events.publishers;

import com.commercial.app.shop_service.es.events.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnShopEsEventPublisher extends ApplicationEvent {
    private final Event shopEsEvent;
    public OnShopEsEventPublisher(Object source, Event shopEsEvent) {
        super(source);
        this.shopEsEvent = shopEsEvent;
    }
}
