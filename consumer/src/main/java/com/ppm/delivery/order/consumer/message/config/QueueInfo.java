package com.ppm.delivery.order.consumer.message.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Queue;

@Getter
@Setter
@AllArgsConstructor
public class QueueInfo {

    private String routingKey;
    private Queue queue;
}
