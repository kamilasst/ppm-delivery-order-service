package com.ppm.delivery.order.producer.api.exception;

public class TimestampInvalidException extends HeaderValidationException {

    public TimestampInvalidException(String message) {
        super(message);
    }
    public TimestampInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}