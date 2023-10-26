package com.commercial.app.shop_service.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    final public static class ES {
        final public static class EVENT_TYPE {
            final public static String SHOP_CREATED = "ShopCreated";
            final public static String SHOP_REMOVED = "ShopRemoved";
            final public static String SHOP_UPDATED = "ShopUpdated";
        }


        final public static class EVENT_STREAM {
            final public static String SHOP = "SHOP";
        }
    }
}
