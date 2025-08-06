package com.ppm.delivery.order.consumer.api.context;

import com.ppm.delivery.order.consumer.api.domain.Context;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class MessageContextHolder {

    private static final ThreadLocal<Context> contextHolder = new ThreadLocal<>();

    public static void setContext(Context context) {
        contextHolder.set(context);
    }

    public static Context getContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
