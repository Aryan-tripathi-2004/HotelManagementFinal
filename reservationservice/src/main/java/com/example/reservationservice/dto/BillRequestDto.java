package com.example.reservationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillRequestDto {
    @NotBlank(message = "Payment method must not be empty")
    private String paymentMethod;
}
