package com.example.reservationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;

    private String name;
    private String email; // Required only for main guest
    private String phoneNumber;
    private String address;
    private String nic; // Aadhaar/PAN etc
    private String gender;
    private int age;
}
