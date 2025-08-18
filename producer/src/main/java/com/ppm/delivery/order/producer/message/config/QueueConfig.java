package com.ppm.delivery.order.producer.message.config;

import ch.qos.logback.core.util.StringUtil;
import com.ppm.delivery.order.producer.exception.MessageErrorConstants;
import com.ppm.delivery.order.producer.properties.ApplicationProperties;
import com.ppm.delivery.order.producer.properties.MessageApplicationProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class QueueConfig {

    private final ApplicationProperties applicationProperties;
    private final MessageApplicationProperties messageApplicationProperties;
    private final Map<String, String> routingKeyMap = new LinkedHashMap<>();

    public QueueConfig(final ApplicationProperties applicationProperties, final MessageApplicationProperties messageApplicationProperties) {
        this.applicationProperties = applicationProperties;
        this.messageApplicationProperties = messageApplicationProperties;
    }

    @PostConstruct
    public void init() {
        for(String country : applicationProperties.getSupportedCountries()) {
            for (String action :  messageApplicationProperties.getActions()) {
                String routingKey = createRoutingKey(country, action);
                routingKeyMap.put(routingKey, routingKey);
            }
        }
    }

    private static String createRoutingKey(String country, String action) {
        return country.toLowerCase() + "." + action;
    }

    public String getRoutingKey(final String country, final String action) {

        if (StringUtil.isNullOrEmpty(country) || StringUtil.isNullOrEmpty(action)) {
            throw new IllegalArgumentException(MessageErrorConstants.ERROR_COUNTRY_AND_ACTION_MUST_NOT_BE_NULL_OR_EMPTY);
        }

        String routingKey = createRoutingKey(country, action);
        if (!routingKeyMap.containsKey(routingKey)) {
            throw new IllegalArgumentException(MessageErrorConstants.ERROR_UNSUPPORTED_COUNTRY_ACTION_COMBINATION +":" + routingKey);
        }

        return routingKey;
    }

    public String getExchangeName() {
        return messageApplicationProperties.getExchange();
    }
}
