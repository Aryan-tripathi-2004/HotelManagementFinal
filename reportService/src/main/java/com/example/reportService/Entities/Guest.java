package com.example.reportService.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Guest {

    private Long guestId;

    private String name;
    private String email; // Required only for main guest
    private String phoneNumber;
    private String address;
    private String nic; // Aadhaar/PAN etc
    private String gender;
    private int age;
}
