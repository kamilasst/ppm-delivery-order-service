package com.ppm.delivery.order.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Value("${app.rabbitmq.domain}")
    private String domain;

    @Value("${app.rabbitmq.country}")
    private String country;

    @Value("${app.rabbitmq.action-create}")
    private String actionCreate;

    @Value("${app.rabbitmq.exchange-command}")
    private String commandExchangeName;

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(commandExchangeName);
    }

    @Bean
    public Queue createQueue() {
        String queueName = country + "." + domain + "." + actionCreate;
        return new Queue(queueName, true);  // durable = true
    }

    @Bean
    public Binding bindingCreateQueue(Queue createQueue, TopicExchange commandExchange) {
        String routingKey = country + "." + actionCreate;
        return BindingBuilder.bind(createQueue).to(commandExchange).with(routingKey);
    }

}