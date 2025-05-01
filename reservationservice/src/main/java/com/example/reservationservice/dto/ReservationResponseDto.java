package com.example.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationResponseDto {
    private Long reservationId;
    private int numberOfChildren;
    private int numberOfAdults;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomNumber;
    private List<String> guestNames;
    private String message;
}
