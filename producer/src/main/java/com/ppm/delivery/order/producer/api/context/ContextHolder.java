package com.ppm.delivery.order.producer.api.context;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@RequestScope
@Component
public class ContextHolder {

    private String country;
    private String correlationId;
    private String profile;
    private String timestamp;

    public void initializeContext(final String country,
                                  final String correlationId,
                                  final String profile,
                                  final String timestamp) {
        this.country = country;
        this.correlationId = correlationId;
        this.profile = profile;
        this.timestamp = timestamp;
    }
}
