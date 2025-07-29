package com.ppm.delivery.order.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    @RabbitListener(queues = "#{createQueue.name}")
    public void receiveCreateMessage(String message) {
        System.out.println("Mensagem recebida na fila create: " + message);
    }

}