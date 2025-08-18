package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;

public interface ISenderMessageService {

    void sendCreateOrderMessage(OrderRequest request);
}
