package com.ppm.delivery.order.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

// TODO atg - Review code sprint 15 - [Pontos mais Gerais] Essas dependências já estão no pom.xml do consumer e producer, avalie add apenas no pom.xml projeto pai(parent)
//        <dependency>
//            <groupId>org.springframework.boot</groupId>
//            <artifactId>spring-boot-starter-web</artifactId>
//        </dependency>
//
//        <dependency>
//            <groupId>org.springframework.boot</groupId>
//            <artifactId>spring-boot-starter-validation</artifactId>
//        </dependency>
@SpringBootApplication
@EnableMongoAuditing // TODO atg - Review code sprint 15 - Pesquisar para que serve essa anotacao e conversar com o arquiteto. Estou achando que era para
// se quiser usar algum @ para fazer update no createAt e UpdateAt, mas, n estamos mais usando esse @
public class PpmDeliveryOrderConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PpmDeliveryOrderConsumerApplication.class, args);
    }
}
