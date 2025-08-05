package com.ppm.delivery.order.producer.config;

import ch.qos.logback.core.util.StringUtil;
import com.ppm.delivery.order.producer.properties.MessageProperties;
import com.ppm.delivery.order.producer.properties.SupportedCountries;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class QueueConfig {

    private final SupportedCountries supportedCountries;
    private final MessageProperties messageProperties;
    private final Map<String, String> routingKeyMap = new LinkedHashMap<>();

    public QueueConfig(final SupportedCountries supportedCountries, final MessageProperties messageProperties) {
        this.supportedCountries = supportedCountries;
        this.messageProperties = messageProperties;
    }

    @PostConstruct
    public void init() {
        for(String country : supportedCountries.getSupportedCountries()) {
            for (String action :  messageProperties.getActions()) {
                String routingKey = country.toLowerCase() + "." + action;
                routingKeyMap.put(routingKey, routingKey);
            }
        }
    }

    public String getRoutingKey(final String country, final String action) {

        if (StringUtil.isNullOrEmpty(country) || StringUtil.isNullOrEmpty(action)) {
            throw new IllegalArgumentException("Country and action must not be null or empty.");
        }

        String routingKey = country.toLowerCase() + "." + action;
        if (!routingKeyMap.containsKey(routingKey)) {
            throw new IllegalArgumentException("Unsupported country-action combination: " + routingKey);
        }

        return routingKey;
    }
}
