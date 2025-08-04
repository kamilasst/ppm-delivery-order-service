package com.ppm.delivery.order.producer.api.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
public class OrderProducerConfig {

    @NotEmpty(message = "Supported countries cannot be empty.")
    private List<String> supportedCountries;

    public OrderProducerConfig() {
        supportedCountries = new ArrayList<>();
    }

    public List<String> getSupportedCountries() {
        return supportedCountries;
    }

    public void setSupportedCountries(List<String> supportedCountries) {
        this.supportedCountries = supportedCountries;
    }
}