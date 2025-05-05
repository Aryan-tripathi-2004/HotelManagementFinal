package com.example.reservationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private int numberOfChildren;
    private int numberOfAdults;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String roomNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Bill bill;
}
