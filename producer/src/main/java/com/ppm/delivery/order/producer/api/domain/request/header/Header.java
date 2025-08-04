package com.ppm.delivery.order.producer.api.domain.request.header;

import java.time.Instant;

public record Header(
        String correlationId,
        Instant timestamp,
        String source,
        String authorization,
        String type,
        Event event,
        UserInfo userInfo,
        Metadata metadata
) {}
