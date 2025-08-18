package com.ppm.delivery.order.producer.api.domain.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfoRequest {

    @NotNull
    @Valid
    private LocationRequest location;
}
