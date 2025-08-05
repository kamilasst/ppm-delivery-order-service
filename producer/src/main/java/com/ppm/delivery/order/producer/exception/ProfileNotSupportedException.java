package com.ppm.delivery.order.producer.exception;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;

public class ProfileNotSupportedException extends HeaderValidationException {
    public ProfileNotSupportedException(String message) {
        super(message);
    }
}