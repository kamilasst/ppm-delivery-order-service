package com.ppm.delivery.order.producer.service;

public interface IOrderService {

    void sendCreateOrderMessage(String message);
}
