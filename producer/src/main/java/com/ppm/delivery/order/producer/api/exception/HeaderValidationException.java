package com.ppm.delivery.order.producer.api.exception;

public class HeaderValidationException extends RuntimeException{
    public HeaderValidationException(String message) {
        super(message);
    }

    public HeaderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}