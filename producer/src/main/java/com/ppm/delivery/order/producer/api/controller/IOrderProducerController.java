package com.ppm.delivery.order.producer.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IOrderProducerController {

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody String message);
}
