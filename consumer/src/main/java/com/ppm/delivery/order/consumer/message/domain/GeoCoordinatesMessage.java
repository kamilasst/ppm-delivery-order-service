package com.ppm.delivery.order.consumer.message.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoCoordinatesMessage {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
