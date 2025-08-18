package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService{

    private final ISenderMessageService senderMessageService;

    public OrderService(ISenderMessageService senderMessageService){
        this.senderMessageService = senderMessageService;
    }

    @Override
    public void sendCreateOrderMessage(OrderRequest request){
        senderMessageService.sendCreateOrderMessage(request);
    }
}
