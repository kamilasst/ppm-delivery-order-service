package com.ppm.delivery.order.consumer.api.context;

import com.ppm.delivery.order.consumer.api.domain.Context;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO atg - Review consumer:
//  1. Muito boa a ideia de utilizar o ThreadLocal !!! e Importante entender o funcionamento dela tb
//  2. No producer vc está chamando de ContextHolder, avalie padronizar
//  3. Para ficar mais fácil a recuperacao de dados do contexto, avalie invés de ter uma classe "Context",
//  mudar para ser um mapa.. pois assim conseguimos obter os valores(country) de forma individual, pois da forma que está
//  teria que pegar o contexto todo e depois fazer um getCountry()
//  exemplo:
//public class ContextHolder {
//    private static final ThreadLocal<Map<String, String>> requestContext = new ThreadLocal<>();
//
//    private ContextHolder() {
//        super();
//    }
//
//    public static Map<String, String> getContext() {
//        return Optional.ofNullable(requestContext.get())
//                .orElseGet(
//                        () -> {
//                            requestContext.set(Collections.synchronizedMap(new HashMap<>()));
//                            return requestContext.get();
//                        });
//    }
// TODO esse aqui será chamado no listener
//public static void initializeContextValues(final String country, final String requestTraceId, final Long timestamp) {
//    setCountry(country);
//    setCorrelationId(requestTraceId);
//    setTimestamp(timestamp);
//}
//public static String getCountry() {
//    return Optional.ofNullable(getContext().get(COUNTRY)).orElse(StringUtils.EMPTY);
//}
//public static void setCountry(final String country) {
//    getContext().put(COUNTRY, country.toUpperCase());
//}
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
