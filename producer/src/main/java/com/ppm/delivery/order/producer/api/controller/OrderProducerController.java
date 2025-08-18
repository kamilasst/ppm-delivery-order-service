package com.ppm.delivery.order.producer.api.controller;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;
import com.ppm.delivery.order.producer.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderProducerController implements IOrderProducerController{

    private final IOrderService orderService;

    public OrderProducerController(final IOrderService orderService) {
        this.orderService = orderService;
    }

   @Override
    public ResponseEntity<String> create(@RequestBody @Valid OrderRequest request) {
        orderService.sendCreateOrderMessage(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
