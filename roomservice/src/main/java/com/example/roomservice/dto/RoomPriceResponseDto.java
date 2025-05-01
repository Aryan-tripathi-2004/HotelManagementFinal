package com.example.roomservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomPriceResponseDto {
    private Long roomId;
    private double basicPrice;
    private double firstNightPrice;
}
