package com.ppm.delivery.order.producer.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final SenderMessageService senderMessageService;

    public OrderService(SenderMessageService senderMessageService){
        this.senderMessageService = senderMessageService;
    }

    public void sendOrder(String message){
        senderMessageService.sendOrder(message);
    }

}
