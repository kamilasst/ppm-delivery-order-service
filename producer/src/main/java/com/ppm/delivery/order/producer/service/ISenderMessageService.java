package com.ppm.delivery.order.producer.service;

public interface ISenderMessageService {

    void sendCreateOrderMessage(Object payload);
}
