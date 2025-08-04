package com.ppm.delivery.order.producer.api.domain.request.header;

public record Header(
        String correlationId,
        String timestamp,
        String source,
        String authorization,
        String type,
        Event event,
        UserInfo userInfo,
        Metadata metadata
) {}
