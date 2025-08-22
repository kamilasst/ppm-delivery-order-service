package com.ppm.delivery.order.consumer.message.domain;

import com.ppm.delivery.order.consumer.domain.enums.ItemCategory;
import com.ppm.delivery.order.consumer.domain.enums.ItemTemperature;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemMessage {

    @NotBlank
    private String id;

    @NotBlank
    private String sku;

    @NotNull
    private ItemCategory category;

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    @NotNull
    private Boolean alcoholic;

    @NotNull
    private ItemTemperature temperature;

    @NotNull
    @Valid
    private PackageInfoMessage packageInfo;

    @NotNull
    @Valid
    private List<@Valid PriceMessage> price;
}
