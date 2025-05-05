package com.example.reportService.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
    private Long billId;

    private String billNumber;
    private String customerName;
    private Double totalAmount;
    private Double tax;
    private Double finalAmount;
    private String paymentMethod;
    private LocalDateTime createdAt;
}
