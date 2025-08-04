package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.constants.ActionMessageConstants;
import com.ppm.delivery.order.producer.api.constants.HeaderConstants;
import com.ppm.delivery.order.producer.api.context.ContextHolder;
import com.ppm.delivery.order.producer.properties.MessagePropertiesAplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class SenderMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final MessagePropertiesAplication messagePropertiesAplication;
    private final ContextHolder contextHolder;

    public SenderMessageService(final RabbitTemplate rabbitTemplate,
                                final MessagePropertiesAplication messagePropertiesAplication,
                                final ContextHolder contextHolder) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagePropertiesAplication = messagePropertiesAplication;
        this.contextHolder = contextHolder;
    }

    public void sendOrder(Object payload) {

        String country = contextHolder.getCountry();
        String routingKey = country.toLowerCase() + "." + ActionMessageConstants.CREATE;

        Message message = createMessage(payload);

        rabbitTemplate.convertAndSend(messagePropertiesAplication.getExchange(), routingKey, message);
    }

    private Message createMessage(Object payload) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(HeaderConstants.HEADER_COUNTRY, contextHolder.getCountry());
        messageProperties.setHeader(HeaderConstants.HEADER_CORRELATION_ID, contextHolder.getCorrelationId());
        messageProperties.setHeader(HeaderConstants.HEADER_PROFILE, contextHolder.getProfile());
        messageProperties.setHeader(HeaderConstants.HEADER_TIMESTAMP, contextHolder.getTimestamp());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        Message message = converter.toMessage(payload, messageProperties);
        return message;
    }
}
