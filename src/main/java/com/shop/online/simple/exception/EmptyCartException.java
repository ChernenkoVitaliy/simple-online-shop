package com.shop.online.simple.exception;

public class EmptyCartException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyCartException(final String message) {
        super(message);
    }
}
