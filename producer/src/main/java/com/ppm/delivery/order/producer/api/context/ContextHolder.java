package com.ppm.delivery.order.producer.api.context;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@RequestScope
@Component
public class ContextHolder {

    private String country;
    private String profile;

    public void initializeContext(final String country, final String profile) {
        this.country = country;
        this.profile = profile;
    }
}
