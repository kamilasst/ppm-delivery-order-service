package com.ppm.delivery.order.consumer.message.listener;

import com.ppm.delivery.order.consumer.message.constants.HeaderConstants;
import com.ppm.delivery.order.consumer.context.ContextHolder;
import com.ppm.delivery.order.consumer.message.domain.OrderMessage;
import com.ppm.delivery.order.consumer.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateListener {

    private final ContextHolder contextHolder;

    private final IOrderService orderService;

    public OrderCreateListener(ContextHolder contextHolder,
                               IOrderService orderService) {
        this.contextHolder = contextHolder;
        this.orderService = orderService;
    }

    @RabbitListener(queues = "#{@rabbitConfig.queueNames()}",
                    containerFactory = "simpleContainerFactory")
    public void receiveMessage(@Header("amqp_receivedRoutingKey") String routingKey,
                               @Header(value = HeaderConstants.HEADER_COUNTRY) final String country,
                               @Header(value = HeaderConstants.HEADER_CORRELATION_ID) final String correlationId,
                               @Header(value = HeaderConstants.HEADER_X_TIMESTAMP) final Long timestamp,
                               @Payload final OrderMessage message) {

        contextHolder.initializeContextValues(country, correlationId, timestamp);


        // TODO atg - Review code sprint 15 - Vamos começar a indrucao a logs e monitoramento, entao remova o try/catch e os system.outs e
        // adicione logs apropriados, adicione a dependencia do import org.slf4j.Logger; no pom.xml do parent(pois será usado no producer e consumer
        // Depois importe no inicio da classe adicione: private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
        // em seguida só usar exemplo: logger.info("Starting process for order: {}", message.getCode()); // Criar constant LogConstants
        try {
            orderService.createOrder(message);

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