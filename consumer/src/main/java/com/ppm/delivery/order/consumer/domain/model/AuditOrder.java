package com.ppm.delivery.order.consumer.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditOrder {

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public AuditOrder(LocalDateTime createAt){
        this.createAt = createAt;
    }
}