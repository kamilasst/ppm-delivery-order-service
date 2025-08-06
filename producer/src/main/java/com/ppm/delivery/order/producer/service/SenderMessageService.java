package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.constants.ActionMessageConstants;
import com.ppm.delivery.order.producer.api.context.ContextHolder;
import com.ppm.delivery.order.producer.message.config.MessageHeaderConstants;
import com.ppm.delivery.order.producer.message.config.QueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SenderMessageService implements ISenderMessageService{

    private final ContextHolder contextHolder;
    private final QueueConfig queueConfig;
    private final RabbitTemplate rabbitTemplate;


    public SenderMessageService(final ContextHolder contextHolder,
                                final QueueConfig queueConfig,
                                final RabbitTemplate rabbitTemplate) {
        this.contextHolder = contextHolder;
        this.queueConfig = queueConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendCreateOrderMessage(Object payload) {

        String routingKey = queueConfig.getRoutingKey(contextHolder.getCountry(), ActionMessageConstants.CREATE);

        Message message = createMessage(payload);

        rabbitTemplate.convertAndSend(queueConfig.getExchangeName(), routingKey, message);
    }

    private Message createMessage(Object payload) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(MessageHeaderConstants.HEADER_COUNTRY, contextHolder.getCountry());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_CORRELATION_ID, contextHolder.getCorrelationId());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_PROFILE, contextHolder.getProfile());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_TIMESTAMP, LocalDateTime.now().toString());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter.toMessage(payload, messageProperties);
    }
}
