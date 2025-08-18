package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;

public interface IOrderService {

    void sendCreateOrderMessage(OrderRequest request);
}
