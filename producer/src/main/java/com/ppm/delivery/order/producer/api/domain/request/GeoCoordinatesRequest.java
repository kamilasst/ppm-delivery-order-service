package com.ppm.delivery.order.producer.api.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoCoordinatesRequest {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
