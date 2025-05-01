package com.example.reservationservice.feign;

import com.example.reservationservice.dto.ReservationRequestDto;
import com.example.reservationservice.dto.ReservationResponseDto;
import com.example.reservationservice.dto.RoomPriceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomFeignClient {

    @PostMapping("/api/rooms/{roomId}/add-reservation")
    void addReservationToRoom(@PathVariable("roomId") Long roomId, @RequestBody ReservationResponseDto reservationRequestDto);

    @GetMapping("/api/rooms/roomNumber/{roomNumber}")
    RoomPriceResponseDto getRoomPricesByRoomNumber(@PathVariable("roomNumber") String roomNumber);
}
