package com.example.roomservice.dto;

import com.example.roomservice.entity.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDto {
    @NotBlank(message = "Room number must not be blank")
    private String roomNumber;

    @NotNull(message = "Room type must not be null")
    private RoomType roomType;

    @Positive(message = "Basic price must be greater than 0")
    private double basicPrice;

    @Positive(message = "First night price must be greater than 0")
    private double firstNightPrice;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
}
