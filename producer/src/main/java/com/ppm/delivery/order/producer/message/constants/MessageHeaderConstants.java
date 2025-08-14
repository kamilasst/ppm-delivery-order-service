package com.ppm.delivery.order.producer.message.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHeaderConstants {

    public static final String HEADER_CORRELATION_ID = "correlationId";
    public static final String HEADER_TIMESTAMP = "timestamp";
    public static final String HEADER_COUNTRY = "country";
    public static final String HEADER_PROFILE = "profile";
}
