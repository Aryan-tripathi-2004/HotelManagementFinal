package com.example.reservationservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillResponseDto {
    private Long BillId;

    private String billNumber;
    private String customerName;
    private Double totalAmount;
    private Double tax;
    private Double finalAmount;
    private String paymentMethod;
    private LocalDateTime createdAt;
}
