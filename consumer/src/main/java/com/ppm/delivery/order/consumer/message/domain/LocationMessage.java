package com.ppm.delivery.order.consumer.message.domain;

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
public class LocationMessage {

    @NotNull
    @Valid
    private GeoCoordinatesMessage geoCoordinates;

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
