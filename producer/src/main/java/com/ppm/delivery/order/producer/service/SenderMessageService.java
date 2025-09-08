package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.constants.ActionMessageConstants;
import com.ppm.delivery.order.producer.api.context.ContextHolder;
import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;
import com.ppm.delivery.order.producer.message.config.QueueConfig;
import com.ppm.delivery.order.producer.message.constants.MessageHeaderConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// TODO atg - Review code sprint 15 - Avalie renomear para SenderOrderMessageService pois ele será responsável
//  exclusivamente por mensagens de order
@Service
public class SenderMessageService implements ISenderMessageService{

    private final ContextHolder contextHolder;
    private final QueueConfig queueConfig;
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;


    public SenderMessageService(final ContextHolder contextHolder,
                                final QueueConfig queueConfig,
                                final RabbitTemplate rabbitTemplate,
                                final MessageConverter messageConverter) {
        this.contextHolder = contextHolder;
        this.queueConfig = queueConfig;
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public void sendCreateOrderMessage(OrderRequest request) {

        String routingKey = queueConfig.getRoutingKey(contextHolder.getCountry(), ActionMessageConstants.CREATE);

        Message message = createMessage(request);

        rabbitTemplate.convertAndSend(queueConfig.getExchangeName(), routingKey, message);
    }

    private Message createMessage(OrderRequest request) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(MessageHeaderConstants.HEADER_COUNTRY, contextHolder.getCountry());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_CORRELATION_ID, contextHolder.getCorrelationId());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_PROFILE, contextHolder.getProfile());
        messageProperties.setHeader(MessageHeaderConstants.HEADER_TIMESTAMP, LocalDateTime.now().toString());

        return messageConverter.toMessage(request, messageProperties);
    }
}
