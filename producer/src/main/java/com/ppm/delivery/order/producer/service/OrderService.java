package com.ppm.delivery.order.producer.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService{

    private final ISenderMessageService senderMessageService;

    public OrderService(ISenderMessageService senderMessageService){
        this.senderMessageService = senderMessageService;
    }

    @Override
    public void sendCreateOrderMessage(String message){
        senderMessageService.sendCreateOrderMessage(message);
    }
}
