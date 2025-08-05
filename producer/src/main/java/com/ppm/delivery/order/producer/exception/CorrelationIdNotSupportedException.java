package com.ppm.delivery.order.producer.exception;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;

public class CorrelationIdNotSupportedException extends HeaderValidationException {
    public CorrelationIdNotSupportedException(String message) {
        super(message);
    }
}