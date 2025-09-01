package com.ppm.delivery.order.consumer.service;

import com.ppm.delivery.order.consumer.context.ContextHolder;
import com.ppm.delivery.order.consumer.domain.mapper.OrderMapper;
import com.ppm.delivery.order.consumer.domain.model.AuditOrder;
import com.ppm.delivery.order.consumer.domain.model.Order;
import com.ppm.delivery.order.consumer.message.domain.OrderMessage;
import com.ppm.delivery.order.consumer.repository.IOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService implements IOrderService{

    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ContextHolder contextHolder;

    public OrderService(final IOrderRepository orderRepository,
                        final OrderMapper orderMapper,
                        final ContextHolder contextHolder){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.contextHolder = contextHolder;
    }

    @Override
    public void createOrder(OrderMessage message){
        Order order = orderMapper.toEntity(message);
        order.setAudit(new AuditOrder(LocalDateTime.now()));
        orderRepository.save(contextHolder.getCountry(), order);

    }

}