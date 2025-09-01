package com.ppm.delivery.order.consumer.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxOrder {

    @NotBlank
    private String id;

    @NotNull
    @Positive
    private BigDecimal value;

}