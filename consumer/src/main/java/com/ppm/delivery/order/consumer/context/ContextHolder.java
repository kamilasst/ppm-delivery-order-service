package com.ppm.delivery.order.consumer.context;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ContextHolder {

    private static final ThreadLocal<Map<String, String>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    private static final String COUNTRY = "country";
    private static final String CORRELATION_ID = "correlationId";
    private static final String TIMESTAMP = "timestamp";

    public void setContext(Map<String, String> context) {
        contextHolder.set(context);
    }

    public Map<String, String> getContext() {
        return contextHolder.get();
    }

    public void initializeContextValues(final String country, final String requestTraceId, final Long timestamp) {
        setCountry(country);
        setCorrelationId(requestTraceId);
        setTimestamp(timestamp);
    }

    private void setCountry(final String country) {
        if (country != null) {
            getContext().put(COUNTRY, country.toUpperCase());
        }
    }

    private void setCorrelationId(final String requestTraceId) {
        if (requestTraceId != null) {
            getContext().put(CORRELATION_ID, requestTraceId.toUpperCase());
        }
    }

    private void setTimestamp(final Long timestamp) {
        if (timestamp != null) {
            getContext().put(TIMESTAMP, timestamp.toString());
        }
    }

    public void clear() {
        contextHolder.remove();
    }
}
