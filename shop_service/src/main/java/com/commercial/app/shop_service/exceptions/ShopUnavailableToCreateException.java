package com.commercial.app.shop_service.exceptions;

public class ShopUnavailableToCreateException extends RuntimeException {
    public ShopUnavailableToCreateException(String message) {
        super(message);
    }
}
