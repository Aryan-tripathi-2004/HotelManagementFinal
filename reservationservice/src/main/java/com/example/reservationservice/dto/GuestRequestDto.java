package com.example.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String nic;
    private String gender;
    private int age;
}
