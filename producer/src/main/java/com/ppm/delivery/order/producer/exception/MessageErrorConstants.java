package com.ppm.delivery.order.producer.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageErrorConstants {

    public static final String ERROR_COUNTRY_REQUIRED_HEADER = "Country is required in the request header";
    public static final String ERROR_COUNTRY_NOT_SUPPORTED = "Country not supported: %s";
    public static final String ERROR_PROFILE_REQUIRED_HEADER = "Profile is required in the request header";
    public static final String ERROR_PROFILE_IS_INVALID = "Profile is Invalid";
    public static final String ERROR_CORRELATION_ID_REQUIRED_HEADER = "CorrelationId is required in the request header";
    public static final String ERROR_TIMESTAMP_REQUIRED_HEADER = "Timestamp is required in the request header";
    public static final String ERROR_COUNTRY_AND_ACTION_MUST_NOT_BE_NULL_OR_EMPTY = "Country and action must not be null or empty.";
    public static final String ERROR_UNSUPPORTED_COUNTRY_ACTION_COMBINATION = "Unsupported country-action combination";
}