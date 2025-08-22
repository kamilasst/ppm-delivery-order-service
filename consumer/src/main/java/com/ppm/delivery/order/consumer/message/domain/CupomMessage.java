package com.ppm.delivery.order.consumer.message.domain;

import com.ppm.delivery.order.consumer.domain.enums.DiscountType;
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
public class CupomMessage {

    @NotBlank
    private String code;

    @NotNull
    private BigDecimal discount;

    @NotNull
    private DiscountType discountType;
}
