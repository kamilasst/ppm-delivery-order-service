package com.ppm.delivery.order.producer.api.controller;

import com.ppm.delivery.order.producer.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderProducerController {

    private final OrderService orderService;

    public OrderProducerController( OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String message) {
        orderService.sendOrder(message);
        return ResponseEntity.accepted().body("Message sent");
    }
}
