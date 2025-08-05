package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.constants.ActionMessageConstants;
import com.ppm.delivery.order.producer.api.constants.HeaderConstants;
import com.ppm.delivery.order.producer.api.context.ContextHolder;
import com.ppm.delivery.order.producer.config.QueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

// TODO atg - Revisao producer - Criar interface ISenderMessageService
@Service
public class SenderMessageService {

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

    // TODO atg - Revisao producer - Avalie renomear para sendCreateOrderMessage pois posterioemente teremos outros
    // requisitos de envio de mensagens
    public void sendOrder(Object payload) {

        String country = contextHolder.getCountry();
        String routingKey = country.toLowerCase() + "." + ActionMessageConstants.CREATE;

        // TODO atg - Revisao producer - Sugestao: Criar o queueConfig para centralizar a configuracao e acesso a dados da exchange e fila
        //String routingKey = queueConfig.getRoutingKey(contextHolder.getCountry(), ActionMessageConstants.CREATE);

        Message message = createMessage(payload);

        rabbitTemplate.convertAndSend(queueConfig.getExchange(), routingKey, message);
    }

    private Message createMessage(Object payload) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(HeaderConstants.HEADER_COUNTRY, contextHolder.getCountry());
        messageProperties.setHeader(HeaderConstants.HEADER_CORRELATION_ID, contextHolder.getCorrelationId());
        messageProperties.setHeader(HeaderConstants.HEADER_PROFILE, contextHolder.getProfile());

        // TODO atg - Revisao producer - Após reavalizar, não deveriamos "pegar" essa data do contexto, pois no contexto
        // trata da data que a requisição foi recebida, e não a data que a mensagem foi enviada... nessa caso deveriamos enviar LocalDateTime.now()
        messageProperties.setHeader(HeaderConstants.HEADER_TIMESTAMP, contextHolder.getTimestamp());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter.toMessage(payload, messageProperties);
    }
}
