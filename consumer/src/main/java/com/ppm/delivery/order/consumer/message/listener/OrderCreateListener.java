package com.ppm.delivery.order.consumer.message.listener;

import com.ppm.delivery.order.consumer.api.constants.HeaderConstants;
import com.ppm.delivery.order.consumer.api.context.MessageContextHolder;
import com.ppm.delivery.order.consumer.api.domain.Context;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    // TODO atg - Review consumer - O @Header por default é required = true, podemos remover
    @RabbitListener(queues = "#{@rabbitConfig.queueNames()}")
    public void receiveMessage(@Header("amqp_receivedRoutingKey") String routingKey,
                               @Header(value = HeaderConstants.HEADER_COUNTRY, required = true) final String country,
                               @Header(value = HeaderConstants.HEADER_CORRELATION_ID, required = true) final String correlationId,
                               @Header(value = HeaderConstants.HEADER_X_TIMESTAMP, required = true) final Long timestamp,
                               @Payload final String message) {

        MessageContextHolder.setContext(new Context(country, correlationId, timestamp));

        try {
            System.out.println("Routing key: " + routingKey);
            System.out.println("Country: " + country);
            System.out.println("Correlation ID: " + correlationId);
            System.out.println("Timestamp: " + timestamp);
            System.out.println("Mensagem: " + message);

        } finally {
            MessageContextHolder.clear();
        }
    }

}