package com.ppm.delivery.order.consumer.domain.model;

import com.ppm.delivery.order.consumer.domain.enums.PackageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageInfoOrder {

    @NotNull
    @Positive
    private Integer itemCount;

    @NotNull
    private PackageType type;

    @NotBlank
    private String unitOfMeasurement;

    @NotNull
    private Boolean returnable;

    @NotNull
    @Positive
    private Integer size;

}