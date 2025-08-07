package com.ppm.delivery.order.consumer.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO atg - Review consumer - No producer chamamos de ApplicationProperties, avalie padronizar
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class SupportedCountries {

    private List<String> supportedCountries;
}
