package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService{

    private final ISenderMessageService senderMessageService;

    public OrderService(ISenderMessageService senderMessageService){
        this.senderMessageService = senderMessageService;
    }

    // TODO atg - Review code sprint 15 - Avalie renomear para sendCreate pois como vc já está no service de order, o createOrder fica redundante
    @Override
    public void sendCreateOrderMessage(OrderRequest request){
        senderMessageService.sendCreateOrderMessage(request);
    }
}
