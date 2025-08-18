package com.ppm.delivery.order.producer.api.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoRequest {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;
}
