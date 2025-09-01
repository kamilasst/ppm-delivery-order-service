package com.ppm.delivery.order.consumer.domain.model;

import com.ppm.delivery.order.consumer.domain.enums.ItemCategory;
import com.ppm.delivery.order.consumer.domain.enums.ItemTemperature;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrder {

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
    private PackageInfoOrder packageInfo;

    @NotEmpty
    @Valid
    private List<@Valid PriceOrder> price;

}