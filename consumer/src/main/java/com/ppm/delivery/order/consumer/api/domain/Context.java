package com.ppm.delivery.order.consumer.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Context {

    private String country;
    private String correlationId;
    private Long timestamp;
}
