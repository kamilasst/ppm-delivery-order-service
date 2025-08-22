package com.ppm.delivery.order.consumer.message.domain;


import com.ppm.delivery.order.consumer.domain.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    @NotBlank
    private String code;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    private Status status;

    @NotNull
    @Valid
    private CustomerInfoMessage customerInfo;

    @NotNull
    @Valid
    private DeliveryInfoMessage deliveryInfo;

    @NotNull
    @Valid
    private List<@Valid ItemMessage> items;

    @NotNull
    @Valid
    private PaymentMessage payment;

    @Valid
    private CupomMessage cupom;
}
