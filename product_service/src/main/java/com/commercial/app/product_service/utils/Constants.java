package com.commercial.app.product_service.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    final public static class ES {
        final public static class EVENT_TYPE {
            final public static String PRODUCT_CREATED = "ProductCreated";
            final public static String PRODUCT_REMOVED = "ProductRemoved";
            final public static String PRODUCT_UPDATED = "ProductUpdated";
        }

        final public static class EVENT_STREAM {
            final public static String PRODUCT = "PRODUCT";
        }
    }
}
