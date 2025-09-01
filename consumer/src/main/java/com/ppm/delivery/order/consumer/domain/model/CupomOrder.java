package com.ppm.delivery.order.consumer.domain.model;

import com.ppm.delivery.order.consumer.domain.enums.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CupomOrder {

    @NotBlank
    private String code;

    @NotNull
    @PositiveOrZero
    private BigDecimal discount;

    @NotNull
    private DiscountType discountType;

}