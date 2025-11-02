package com.ppm.delivery.order.consumer.message.config.rabbitmq.recover;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;

public class MessageRecovererAdapter implements MethodInvocationRecoverer<Void> {

    private final MessageRecoverer recoverer;

    public MessageRecovererAdapter(MessageRecoverer recoverer) {
        this.recoverer = recoverer;
    }

    @Override
    public Void recover(Object[] args, Throwable cause) {
        if (args != null && args.length > 0 && args[1] instanceof Message message) {
            recoverer.recover(message, cause);
        }
        return null;
    }
}
