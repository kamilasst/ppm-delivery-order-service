package com.ppm.delivery.order.consumer.domain.model;

import com.ppm.delivery.order.consumer.domain.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Order {

    @Id
    private String id;

    @NotBlank
    private String code;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    private Status status;

    @NotNull
    @Valid
    private CustomerInfoOrder customerInfo;

    @NotNull
    @Valid
    private DeliveryInfoOrder deliveryInfo;

    @NotNull
    @Valid
    private List<@Valid ItemOrder> items;

    @NotNull
    @Valid
    private PaymentOrder payment;

    @Valid
    private CupomOrder cupom;

    private AuditOrder audit;

}