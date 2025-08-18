package com.ppm.delivery.order.producer.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class ApplicationProperties {

    @NotEmpty(message = "Supported countries cannot be empty.")
    private List<String> supportedCountries;

    public ApplicationProperties() {
        supportedCountries = new ArrayList<>();
    }
}