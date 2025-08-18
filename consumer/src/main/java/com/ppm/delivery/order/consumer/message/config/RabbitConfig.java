package com.ppm.delivery.order.consumer.message.config;

import com.ppm.delivery.order.consumer.properties.MessageApplicationProperties;
import com.ppm.delivery.order.consumer.properties.ApplicationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private final ApplicationProperties applicationProperties;
    private final MessageApplicationProperties messageApplicationProperties;

    public RabbitConfig(ApplicationProperties applicationProperties, MessageApplicationProperties messageApplicationProperties){
        this.applicationProperties = applicationProperties;
        this.messageApplicationProperties = messageApplicationProperties;
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(messageApplicationProperties.getExchange());
    }

    @Bean
    public Map<String, QueueInfo> queueMapInfo() {

        Map<String, QueueInfo> queueMap = new HashMap<>();

        String domain = messageApplicationProperties.getDomain();
        for(String country : applicationProperties.getSupportedCountries()) {
            for (String action :  messageApplicationProperties.getActions()) {

                String queueName = country.toLowerCase() + "." + domain + "." + action;
                String routingKey = country.toLowerCase() + "." + action;
                Queue queue = new Queue(queueName, true);

                queueMap.put(queueName, new QueueInfo(routingKey, queue));
            }
        }
        return queueMap;
    }

    @Bean
    public List<String> queueNames() {
        return queueMapInfo().values().stream()
                .map(queueInfo-> queueInfo.getQueue().getName())
                .toList();
    }

    @Bean
    public List<Binding> createBindings(TopicExchange commandExchange, Map<String, QueueInfo> queueInfoMap) {
        return queueInfoMap.values().stream()
                .map(qi -> BindingBuilder
                        .bind(qi.getQueue())
                        .to(commandExchange)
                        .with(qi.getRoutingKey()))
                .toList();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory,
                                   TopicExchange commandExchange,
                                   Map<String, QueueInfo> queueMapInfo,
                                   List<Binding> createBindings) {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(commandExchange);

        for (QueueInfo queueInfo : queueMapInfo.values()) {
            admin.declareQueue(queueInfo.getQueue());
        }

        for (Binding binding : createBindings) {
            admin.declareBinding(binding);
        }

        return admin;
    }

}