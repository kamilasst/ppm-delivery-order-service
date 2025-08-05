package com.ppm.delivery.order.producer.config;

import ch.qos.logback.core.util.StringUtil;
import com.ppm.delivery.order.producer.properties.MessagePropertiesAplication;
import com.ppm.delivery.order.producer.properties.SupportedCountriesProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class QueueConfig {

    private final SupportedCountriesProperties supportedCountriesProperties;
    private final MessagePropertiesAplication messagePropertiesAplication;
    private final Map<String, String> routingKeyMap = new LinkedHashMap<>();

    public QueueConfig(final SupportedCountriesProperties supportedCountriesProperties, final MessagePropertiesAplication messagePropertiesAplication) {
        this.supportedCountriesProperties = supportedCountriesProperties;
        this.messagePropertiesAplication = messagePropertiesAplication;
    }

    @PostConstruct
    public void init() {
        for(String country : supportedCountriesProperties.getSupportedCountries()) {
            for (String action :  messagePropertiesAplication.getActions()) {
                String routingKey = country.toLowerCase() + "." + action;
                routingKeyMap.put(routingKey, routingKey);
            }
        }
    }

    public String getRoutingKey(final String country, final String action) {

        if (StringUtil.isNullOrEmpty(country) || StringUtil.isNullOrEmpty(action)) {
            // TODO atg - Revisao producer - Adcionar as msgs de erro em MessageErrorConstants
            throw new IllegalArgumentException("Country and action must not be null or empty.");
        }

        String routingKey = country.toLowerCase() + "." + action;
        if (!routingKeyMap.containsKey(routingKey)) {
            // TODO atg - Revisao producer - Adcionar as msgs de erro em MessageErrorConstants
            throw new IllegalArgumentException("Unsupported country-action combination: " + routingKey);
        }

        return routingKey;
    }

    public String getExchange() {
        return messagePropertiesAplication.getExchange();
    }
}
