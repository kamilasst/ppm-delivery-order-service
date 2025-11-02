package com.ppm.delivery.order.consumer.message.config.rabbitmq;

import com.ppm.delivery.order.consumer.message.config.rabbitmq.recover.CustomMessageRecover;
import com.ppm.delivery.order.consumer.message.config.rabbitmq.recover.MessageRecovererAdapter;
import com.ppm.delivery.order.consumer.properties.ApplicationProperties;
import com.ppm.delivery.order.consumer.properties.MessageApplicationProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private final ApplicationProperties applicationProperties;
    private final MessageApplicationProperties messageApplicationProperties;

    public RabbitConfig(final ApplicationProperties applicationProperties,
                        final MessageApplicationProperties messageApplicationProperties) {
        this.applicationProperties = applicationProperties;
        this.messageApplicationProperties = messageApplicationProperties;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(messageApplicationProperties.getExchange());
    }

    @Bean
    public TopicExchange dlqCommandExchange() {
        return new TopicExchange(messageApplicationProperties.getDlqExchange());
    }

    @Bean
    public Map<String, QueueInfo> queueMapInfo() {

        Map<String, QueueInfo> queueMap = new HashMap<>();

        String domain = messageApplicationProperties.getDomain();
        for (String country : applicationProperties.getSupportedCountries()) {
            for (String action : messageApplicationProperties.getActions()) {

                String queueName = country.toLowerCase() + "." + domain + "." + action;
                String routingKey = country.toLowerCase() + "." + action;
                Queue queue = new Queue(queueName, true);

                queueMap.put(queueName, new QueueInfo(routingKey, queue));
            }
        }
        return queueMap;
    }

    // TODO atg Criar testes unitarios
    @Bean
    public Map<String, QueueInfo> queueMapDlqInfo() {

        Map<String, QueueInfo> queueMapDlq = new HashMap<>();

        String domain = messageApplicationProperties.getDomain();

        for (String country : applicationProperties.getSupportedCountries()) {
            for (String action : messageApplicationProperties.getActions()) {

                String queueNameDlq = country.toLowerCase() + "." + domain + "." + action + ".dlq";
                String routingKey = country.toLowerCase() + "." + action;
                Queue queueDlq = QueueBuilder.durable(queueNameDlq).build();

                queueMapDlq.put(queueNameDlq, new QueueInfo(routingKey + ".dlq", queueDlq));
            }
        }
        return queueMapDlq;
    }

    @Bean
    public List<String> queueNames() {
        return queueMapInfo().values().stream()
                .map(queueInfo -> queueInfo.getQueue().getName())
                .toList();
    }

    @Bean
    public List<Binding> createBindings(@Qualifier("commandExchange") TopicExchange commandExchange,
                                        @Qualifier("queueMapInfo") Map<String, QueueInfo> queueInfoMap) {
        return createGeneralBindings(commandExchange, queueInfoMap);
    }

    @Bean
    public List<Binding> createDlqBindings(@Qualifier("dlqCommandExchange") TopicExchange dlqCommandExchange,
                                           @Qualifier("queueMapDlqInfo") Map<String, QueueInfo> queueMapDlqInfo) {
        return createGeneralBindings(dlqCommandExchange, queueMapDlqInfo);
    }

    private List<Binding> createGeneralBindings(TopicExchange exchange, Map<String, QueueInfo> queueMap) {
        return queueMap.values().stream()
                .map(qi -> BindingBuilder
                        .bind(qi.getQueue())
                        .to(exchange)
                        .with(qi.getRoutingKey()))
                .toList();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory,
                                   TopicExchange commandExchange,
                                   TopicExchange dlqCommandExchange,
                                   Map<String, QueueInfo> queueMapInfo,
                                   Map<String, QueueInfo> queueMapDlqInfo,
                                   List<Binding> createBindings,
                                   List<Binding> createDlqBindings) {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(commandExchange);
        admin.declareExchange(dlqCommandExchange);
        admin.setAutoStartup(true);
        admin.setIgnoreDeclarationExceptions(true);

        declareQueue(admin, queueMapInfo.values());
        declareQueue(admin, queueMapDlqInfo.values());

        declareBinding(admin, createBindings);
        declareBinding(admin, createDlqBindings);

        return admin;
    }

    private void declareQueue(RabbitAdmin admin, Collection<QueueInfo> queueInfos) {
        for (QueueInfo queueInfo : queueInfos) {
            admin.declareQueue(queueInfo.getQueue());
        }
    }

    private void declareBinding(RabbitAdmin admin, List<Binding> bindings) {
        for (Binding binding : bindings) {
            admin.declareBinding(binding);
        }
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // TODO atg Pegar valores fixos do application
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(2);
        retryTemplate.setRetryPolicy(retryPolicy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(2000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }


    @Bean
    public CustomMessageRecover customMessageRecover(RabbitTemplate rabbitTemplate,
                                                     @Qualifier("dlqCommandExchange") TopicExchange dlqCommandExchange) {
        return new CustomMessageRecover(rabbitTemplate, dlqCommandExchange.getName());
    }


    @Bean
    public SimpleRabbitListenerContainerFactory simpleContainerFactory(
            final ConnectionFactory connectionFactory,
            final SimpleRabbitListenerContainerFactoryConfigurer configurer,
            final Jackson2JsonMessageConverter jackson2JsonMessageConverter,
            final RetryTemplate retryTemplate,
            final CustomMessageRecover customMessageRecover) {

        final var factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);

        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate)
                .recoverer(new MessageRecovererAdapter(customMessageRecover))
                .build());

        return factory;
    }

}