package com.example.reservationservice.service;

import com.example.reservationservice.dto.GuestRequestDto;

import java.util.List;

public interface GuestService {
    List<GuestRequestDto> getAllGuests();
    GuestRequestDto getGuestById(Long guestId);
}
