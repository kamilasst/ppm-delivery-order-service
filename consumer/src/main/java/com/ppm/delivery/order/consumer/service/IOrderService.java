package com.ppm.delivery.order.consumer.service;

import com.ppm.delivery.order.consumer.message.domain.OrderMessage;

public interface IOrderService {

    // TODO atg - Review code sprint 15 - Avalie renomear para create pois como vc já está na interface de OrderService, o sufixo Order fica redundante
    void createOrder(OrderMessage message);

}