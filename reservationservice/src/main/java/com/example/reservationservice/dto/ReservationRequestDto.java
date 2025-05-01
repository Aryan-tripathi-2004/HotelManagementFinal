package com.example.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationRequestDto {
    private int numberOfChildren;
    private int numberOfAdults;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomNumber;
    private List<GuestRequestDto> guests;
    private BillRequestDto billRequest;
}
