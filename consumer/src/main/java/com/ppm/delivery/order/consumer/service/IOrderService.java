package com.ppm.delivery.order.consumer.service;

import com.ppm.delivery.order.consumer.message.domain.OrderMessage;

public interface IOrderService {

    void createOrder(OrderMessage message);

}