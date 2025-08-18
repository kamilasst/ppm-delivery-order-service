package com.ppm.delivery.order.producer.api.domain.request;

import jakarta.validation.Valid;
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
public class LocationRequest {

    @NotNull
    @Valid
    private GeoCoordinatesRequest geoCoordinates;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String number;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String streetAddress;
}
