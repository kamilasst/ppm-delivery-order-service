package com.ppm.delivery.order.consumer.message.domain;

import com.ppm.delivery.order.consumer.domain.enums.PackageType;
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
public class PackageInfoMessage {

    @NotNull
    private Integer itemCount;

    @NotNull
    private PackageType type;

    @NotBlank
    private String unitOfMeasurement;

    @NotNull
    private Boolean returnable;

    @NotNull
    private Integer size;
}
