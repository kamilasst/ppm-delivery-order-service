package com.ppm.delivery.order.consumer.message.listener;

import com.ppm.delivery.order.consumer.message.constants.HeaderConstants;
import com.ppm.delivery.order.consumer.context.ContextHolder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    private final ContextHolder contextHolder;

    public OrderCreateListener(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @RabbitListener(queues = "#{@rabbitConfig.queueNames()}")
    public void receiveMessage(@Header("amqp_receivedRoutingKey") String routingKey,
                               @Header(value = HeaderConstants.HEADER_COUNTRY) final String country,
                               @Header(value = HeaderConstants.HEADER_CORRELATION_ID) final String correlationId,
                               @Header(value = HeaderConstants.HEADER_X_TIMESTAMP) final Long timestamp,
                               @Payload final String message) {

        contextHolder.initializeContextValues(country, correlationId, timestamp);

        try {
            System.out.println("Routing key: " + routingKey);
            System.out.println("Country: " + country);
            System.out.println("Correlation ID: " + correlationId);
            System.out.println("Timestamp: " + timestamp);
            System.out.println("Mensagem: " + message);

        } finally {
            contextHolder.clear();
        }
    }

}