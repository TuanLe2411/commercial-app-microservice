package com.commercial.app.product_service.exceptions;

public class ProductUnavailableToChange extends RuntimeException {
    public ProductUnavailableToChange(String message) {
        super(message);
    }
}
