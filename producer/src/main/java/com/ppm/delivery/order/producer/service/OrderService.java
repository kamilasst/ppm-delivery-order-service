package com.ppm.delivery.order.producer.service;

import org.springframework.stereotype.Service;

// TODO atg - Revisao producer - Criar interface IOrderService
@Service
public class OrderService {

    // TODO atg - Acessar via interface
    private final SenderMessageService senderMessageService;

    public OrderService(SenderMessageService senderMessageService){
        this.senderMessageService = senderMessageService;
    }

    // TODO atg - Revisao producer - Avalie renomear para sendCreateOrderMessage
    public void sendOrder(String message){
        senderMessageService.sendOrder(message);
    }

}
