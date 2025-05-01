package com.example.roomservice.dto;

import com.example.roomservice.entity.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDto {
    private Long roomId;
    private String roomNumber;
    private RoomType roomType;
    private double basicPrice;
    private double firstNightPrice;
    private int capacity;
}
