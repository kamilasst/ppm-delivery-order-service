package com.ppm.delivery.order.consumer.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInfoOrder {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

}