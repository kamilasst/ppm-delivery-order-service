package com.ppm.delivery.order.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PpmDeliveryOrderConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PpmDeliveryOrderConsumerApplication.class, args);
    }
}
