package com.ppm.delivery.order.producer.api.controller;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IOrderProducerController {

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody @Valid OrderRequest request);
}
