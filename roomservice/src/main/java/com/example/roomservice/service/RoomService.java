package com.example.roomservice.service;

import com.example.roomservice.dto.ReservationInputDto;
import com.example.roomservice.dto.RoomPriceResponseDto;
import com.example.roomservice.dto.RoomRequestDto;
import com.example.roomservice.dto.RoomResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);
    RoomResponseDto updateRoom(Long id, RoomRequestDto roomRequestDto);
    void deleteRoom(Long id);
    List<RoomResponseDto> getAllRooms();
    RoomResponseDto getRoomById(Long id);
    void addReservationToRoom(Long roomId, ReservationInputDto reservationInputDto);
    List<RoomResponseDto> getAvailableRoomsByDate(LocalDate date);
    RoomPriceResponseDto getRoomPrices(String roomNumber);
}
