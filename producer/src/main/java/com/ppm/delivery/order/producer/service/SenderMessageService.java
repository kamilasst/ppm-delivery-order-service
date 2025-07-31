package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.properties.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageProperties messageProperties;

    public SenderMessageService(RabbitTemplate rabbitTemplate, MessageProperties messageProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageProperties = messageProperties;
    }

    public void sendOrder(Object payload) {
        String routingKey = "ar" + "." + messageProperties.getActions().get(0);
        System.out.println("Sending message to routingKey: " + routingKey);
        rabbitTemplate.convertAndSend(messageProperties.getExchange(), routingKey, payload);
    }
}
