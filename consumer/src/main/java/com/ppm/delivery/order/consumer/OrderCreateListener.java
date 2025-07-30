package com.ppm.delivery.order.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    @RabbitListener(queues = "#{rabbitConfig.queueNames()}")
    public void receiveMessage(@Header("amqp_receivedRoutingKey") String routingKey,
                               String message) {

        System.out.println("Mensagem recebida: " + message);
        System.out.println("Routing key: " + routingKey);

        String country = routingKey.split("\\.")[0];
        System.out.println("🌍 País da mensagem: " + country);
    }

}