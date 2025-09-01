package com.ppm.delivery.order.consumer.repository;

import com.ppm.delivery.order.consumer.domain.model.Order;


public interface IOrderRepository {

    void save(final String country, Order order);

}