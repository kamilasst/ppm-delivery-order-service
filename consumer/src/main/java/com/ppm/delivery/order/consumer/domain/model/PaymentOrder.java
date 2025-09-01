package com.ppm.delivery.order.consumer.domain.model;

import com.ppm.delivery.order.consumer.domain.enums.DiscountType;
import com.ppm.delivery.order.consumer.domain.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrder {

    @NotNull
    private PaymentMethod method;

    @NotNull
    @PositiveOrZero
    private BigDecimal discount;

    @NotNull
    private DiscountType discountType;

    @NotNull
    @Positive
    private BigDecimal total;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxAmount;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Valid
    private List<@Valid TaxOrder> taxes;

}