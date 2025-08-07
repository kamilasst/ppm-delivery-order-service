package com.ppm.delivery.order.consumer.config;

import com.ppm.delivery.order.consumer.properties.MessageProperties;
import com.ppm.delivery.order.consumer.properties.SupportedCountries;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private final SupportedCountries supportedCountries;
    private final MessageProperties messageProperties;
    private final Map<String, String> queueNameRoutingKeyMap = new HashMap<>();

    public RabbitConfig(SupportedCountries supportedCountries, MessageProperties messageProperties){
        this.supportedCountries = supportedCountries;
        this.messageProperties = messageProperties;
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(messageProperties.getExchange());
    }

    // TODO atg - Revisao consumer - Quando fizemos o countryQueues e adicionamos o queueNameRoutingKeyMap, o metodo ficou
    // com duas responsabilidades, entao a sugestao é refatorar List<Queue> countryQueues() para public Map<String, QueueInfo> queueMapInfo() {
    // acredito que isso resolve o problema... ai teria que alterar os outros metodos para usa-lo
//    @Getter
//    @AllArgsConstructor
//    public class QueueInfo {
//
//        private String routingKey;
//        private Queue queue;
//    }
    @Bean
    public List<Queue> countryQueues() {

        List<Queue> countryQueues = new ArrayList<>();
        String domain = messageProperties.getDomain();
        for(String country : supportedCountries.getSupportedCountries()) {
            for (String action :  messageProperties.getActions()) {
                String queueName = country.toLowerCase() + "." + domain + "." + action;
                countryQueues.add(new Queue(queueName, true));
                String routingKey = country.toLowerCase() + "." + action;
                queueNameRoutingKeyMap.put(queueName, routingKey);
            }
        }
        return countryQueues;
    }

    @Bean
    public List<String> queueNames() {
        return countryQueues().stream()
                .map(Queue::getName)
                .toList();
    }

    @Bean
    public List<Binding> createBindings(TopicExchange commandExchange, List<Queue> countryQueues) {
        List<Binding> bindings = new ArrayList<>();
        for (Queue queue : countryQueues) {
            String routingKey = queueNameRoutingKeyMap.get(queue.getName());
            bindings.add(BindingBuilder.bind(queue).to(commandExchange).with(routingKey));
        }
        return bindings;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory,
                                   TopicExchange commandExchange,
                                   List<Queue> countryQueues,
                                   List<Binding> createBindings) {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(commandExchange);

        for (Queue queue : countryQueues) {
            admin.declareQueue(queue);
        }

        for (Binding binding : createBindings) {
            admin.declareBinding(binding);
        }

        return admin;
    }

}