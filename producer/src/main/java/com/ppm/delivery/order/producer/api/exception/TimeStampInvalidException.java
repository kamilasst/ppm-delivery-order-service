package com.ppm.delivery.order.producer.api.exception;

public class TimeStampInvalidException extends HeaderValidationException {

    public TimeStampInvalidException(String message) {
        super(message);
    }
    public TimeStampInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}