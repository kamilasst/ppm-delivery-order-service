package com.ppm.delivery.order.producer.exception;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;

public class TimeStampNotSupportedException extends HeaderValidationException {
    public TimeStampNotSupportedException(String message) {
        super(message);
    }
}