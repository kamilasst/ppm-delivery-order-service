package com.ppm.delivery.order.producer.api.controller;

import com.ppm.delivery.order.producer.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO atg - Revisao producer - Criar interface IOrderProducerController
@RestController
@RequestMapping("/order")
public class OrderProducerController {

    // TODO atg - Revisao producer - Acessar via interface
    private final OrderService orderService;

    public OrderProducerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String message) {
        orderService.sendOrder(message);

        // TODO atg - Revisao producer - O HttpStatus.ACCEPTED pode ser ser body nesse caso
        // return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        return ResponseEntity.accepted().body("Message sent");
    }
}
