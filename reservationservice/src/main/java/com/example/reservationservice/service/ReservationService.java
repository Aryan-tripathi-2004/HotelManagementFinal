package com.example.reservationservice.service;

import com.example.reservationservice.dto.ReservationRequestDto;
import com.example.reservationservice.dto.ReservationResponseDto;

import java.util.List;

public interface ReservationService {
    ReservationResponseDto createReservation(ReservationRequestDto requestDto);
    ReservationResponseDto getReservationById(Long id);
    List<ReservationResponseDto> getAllReservations();
    void deleteReservation(Long id);
    ReservationResponseDto updateReservation(Long id, ReservationRequestDto requestDto);
}
