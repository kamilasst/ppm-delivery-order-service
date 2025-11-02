package com.ppm.delivery.order.consumer.message.config.rabbitmq.recover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageRecover implements MessageRecoverer {

    private static final Logger log = LoggerFactory.getLogger(CustomMessageRecover.class);
    private final RabbitTemplate rabbitTemplate;
    private final String dlqExchange;

    public CustomMessageRecover(RabbitTemplate rabbitTemplate, String dlqExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.dlqExchange = dlqExchange;
    }

    @Override
    public void recover(Message message, Throwable cause) {
        log.error("Message failed after all retry attempts: {}", new String(message.getBody()), cause);

        MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setHeader("x-exception-message", cause.getCause());

        Message deadLetterMessage = MessageBuilder.fromMessage(message)
                .andProperties(messageProperties)
                .build();

        String routingKey = message.getMessageProperties().getReceivedRoutingKey() + ".dlq";
        rabbitTemplate.send(dlqExchange, routingKey, deadLetterMessage);
    }
}
