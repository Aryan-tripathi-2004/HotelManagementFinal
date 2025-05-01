package com.example.reservationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billNumber;
    private String customerName;
    private LocalDateTime date;
    private Double totalAmount;
    private Double tax;
    private Double finalAmount;
    private String paymentMethod;
    private LocalDateTime createdAt;
}
