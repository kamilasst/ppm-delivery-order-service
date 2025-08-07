package com.ppm.delivery.order.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO atg - Review consumer: Vou add aqui os pontos gerais...
// 1. Excluir pacote api dentro don consumer por ele n ser uma api
@SpringBootApplication
public class PpmDeliveryOrderConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PpmDeliveryOrderConsumerApplication.class, args);
    }
}
