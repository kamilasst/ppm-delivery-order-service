package com.ppm.delivery.order.producer.api.domain.request;

import com.ppm.delivery.order.producer.domain.enums.DiscountType;
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
public class CupomRequest {

    @NotBlank
    private String code;

    @NotNull
    private BigDecimal discount;

    @NotBlank
    private DiscountType discountType;
}
