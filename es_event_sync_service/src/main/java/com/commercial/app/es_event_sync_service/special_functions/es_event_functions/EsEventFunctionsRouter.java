package com.commercial.app.es_event_sync_service.special_functions.es_event_functions;

import com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions.ProductDeleteHandler;
import com.commercial.app.es_event_sync_service.es.events.Event;
import com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions.ProductCreatedHandler;
import com.commercial.app.es_event_sync_service.special_functions.es_event_functions.product_functions.ProductUpdatedHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EsEventFunctionsRouter {
    final ProductCreatedHandler productCreatedHandler;
    final ProductUpdatedHandler productUpdatedHandler;
    final ProductDeleteHandler productDeleteHandler;

    public EsEventFunctionsRouter(
        ProductCreatedHandler productCreatedHandler,
        ProductUpdatedHandler productUpdatedHandler,
        ProductDeleteHandler productDeleteHandler
    ) {
        this.productCreatedHandler = productCreatedHandler;
        this.productUpdatedHandler = productUpdatedHandler;
        this.productDeleteHandler = productDeleteHandler;
    }

    public void route(Event event) {
        System.out.println("begin handle event: " + event.toString());
        Runnable routeHandle = this.getStreamRoute(event);
        routeHandle.run();
        System.out.println("ended handle event: " + event);
    }

    private Runnable getStreamRoute(Event event) {
        Map<String, Runnable> typeRoute = getTypeRouter(event);
        return typeRoute.get(event.getType());
    }

    private Map<String, Runnable> getTypeRouter(Event event) {
        Map<String, Runnable> typeRoute = new HashMap<>();
        if (event.getStream().equals("PRODUCT")) {
            typeRoute.put("ProductCreated",
                () -> this.productCreatedHandler.handle(event.getStreamId(), event.getPayload()));
            typeRoute.put("ProductUpdated",
                () -> this.productUpdatedHandler.handle(event.getStreamId(), event.getPayload()));
            typeRoute.put("ProductRemoved",
                () -> this.productDeleteHandler.handle(event.getStreamId()));
        }
        return typeRoute;
    }
}
