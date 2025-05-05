package com.example.reservationservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationRequestDto {
    @Min(value = 0, message = "Number of children cannot be negative")
    private int numberOfChildren;

    @Min(value = 1, message = "At least one adult is required")
    private int numberOfAdults;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotEmpty(message = "At least one guest is required")
    private List<@Valid GuestRequestDto> guests;

    @NotNull(message = "Bill information is required")
    @Valid
    private BillRequestDto billRequest;
}
