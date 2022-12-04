package com.shop.online.simple.exception;

public class ProductAlreadyAddedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductAlreadyAddedException(final String message) {
        super(message);
    }
}
