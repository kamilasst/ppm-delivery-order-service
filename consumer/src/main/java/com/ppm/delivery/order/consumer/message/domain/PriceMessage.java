package com.ppm.delivery.order.consumer.message.domain;

import com.ppm.delivery.order.consumer.domain.enums.PriceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceMessage {

    @NotBlank
    private String id;

    @NotNull
    private PriceType type;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal total;

    @NotNull
    private BigDecimal discountAmount;
}
