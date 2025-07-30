package com.ppm.delivery.order.consumer.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "message.rabbitmq")
public class MessageProperties {

    private String domain;
    private List<String> actions = new ArrayList<>();
    private String exchange;
}
