package com.example.reportService.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomResponseDto {
    private Long roomId;
    private String roomNumber;
    private RoomType roomType;
    private double basicPrice;
    private double firstNightPrice;
    private int capacity;
    private List<InventoryRequestDto> inventories;

}
