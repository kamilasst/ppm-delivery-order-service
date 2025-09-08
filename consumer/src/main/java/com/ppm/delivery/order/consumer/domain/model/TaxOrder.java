package com.ppm.delivery.order.consumer.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

// TODO atg - Review code sprint 15 - Por favor avalie renomear todas as classses de domain model que terminam com Order
//  pois como vc já está no package order, o sufixo Order fica redundante. Exemplo: TaxOrder -> Tax, PriceOrder -> Price, ItemOrder -> Item, etc.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxOrder {

    @NotBlank
    private String id;

    @NotNull
    @Positive
    private BigDecimal value;

}