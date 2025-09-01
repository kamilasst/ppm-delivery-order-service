package com.ppm.delivery.order.consumer.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationOrder {

    @NotNull
    @Valid
    private GeoCoordinatesOrder geoCoordinates;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String number;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Invalid ZIP code, use the format 00000-000")
    private String zipCode;

    @NotBlank
    private String streetAddress;

}