package com.ppm.delivery.order.consumer.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfoOrder {

    @NotNull
    @Valid
    private LocationOrder location;

}