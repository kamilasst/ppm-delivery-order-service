package com.ppm.delivery.order.producer.exception;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;

public class CountryNotSupportedException extends HeaderValidationException {
    public CountryNotSupportedException(String message) {
        super(message);
    }
}