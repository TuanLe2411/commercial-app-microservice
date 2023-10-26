package com.commercial.app.shop_service.exceptions;

public class ShopUnavailableToChangeException extends RuntimeException {
    public ShopUnavailableToChangeException(String message) {
        super(message);
    }
}
