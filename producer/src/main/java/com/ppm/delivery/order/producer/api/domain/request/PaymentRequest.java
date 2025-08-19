package com.ppm.delivery.order.producer.api.domain.request;

import com.ppm.delivery.order.producer.domain.enums.DiscountType;
import com.ppm.delivery.order.producer.domain.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull
    private PaymentMethod method;

    @NotNull
    private BigDecimal discount;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private BigDecimal total;

    @NotNull
    private BigDecimal taxAmount;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Valid
    private List<@Valid TaxRequest> taxes;
}
