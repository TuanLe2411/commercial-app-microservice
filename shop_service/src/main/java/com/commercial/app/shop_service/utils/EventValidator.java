package com.commercial.app.shop_service.utils;

import com.commercial.app.shop_service.es.events.Event;
import lombok.Builder;

import java.util.Objects;

@Builder
public class EventValidator {
    private Event event;

    public boolean isUserCreatedShop() {
        return this.event != null && !this.event.getType().equals(Constants.ES.EVENT_TYPE.SHOP_REMOVED);
    }

    public boolean isShopUnCreated() {
        return this.event == null;
    }

    public boolean isShopRemoved() {
        return Objects.equals(this.event.getType(), Constants.ES.EVENT_TYPE.SHOP_REMOVED);
    }

    public boolean isOwnerOfShop(String ownerId) {
        return ownerId.equals(this.event.getOwnerId());
    }
}
