package com.ppm.delivery.order.producer.api.domain.request;

import com.ppm.delivery.order.producer.domain.enums.Status;
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
public class OrderRequest {

    @NotBlank
    private String code;

    @NotNull
    private LocalDateTime createDate;

    @NotBlank
    private Status status;

    @NotNull
    @Valid
    private CustomerInfoRequest customerInfo;

    @NotNull
    @Valid
    private DeliveryInfoRequest deliveryInfo;

    @NotNull
    @Valid
    private List<@Valid ItemRequest> items;

    @NotNull
    @Valid
    private PaymentRequest payment;

    @Valid
    private CupomRequest cupom;
}
