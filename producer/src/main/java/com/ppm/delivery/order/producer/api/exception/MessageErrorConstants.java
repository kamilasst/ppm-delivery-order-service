package com.ppm.delivery.order.producer.api.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageErrorConstants {

    public static final String ERROR_COUNTRY_REQUIRED_HEADER = "Country is required in the request header";
    public static final String ERROR_COUNTRY_NOT_SUPPORTED = "Country not supported: %s";
    public static final String ERROR_PROFILE_REQUIRED_HEADER = "Profile is required in the request header";
    public static final String ERROR_PROFILE_IS_INVALID = "Profile is Invalid";
    public static final String ERROR_CORRELATION_ID_REQUIRED_HEADER = "CorrelationId is required in the request header";
    public static final String ERROR_TIME_STAMP_REQUIRED_HEADER = "TimeStamp is required in the request header";
}