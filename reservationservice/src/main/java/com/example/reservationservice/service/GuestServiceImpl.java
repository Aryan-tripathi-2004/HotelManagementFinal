package com.example.reservationservice.service;

import com.example.reservationservice.dto.GuestRequestDto;
import com.example.reservationservice.entity.Guest;
import com.example.reservationservice.repository.GuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GuestRequestDto> getAllGuests() {
        List<Guest> guests = guestRepository.findAll();
        if (guests.isEmpty()) {
            throw new RuntimeException("No guests found");
        }
        return guests.stream()
                .map(guest -> modelMapper.map(guest, GuestRequestDto.class))
                .toList();

    }

    @Override
    public GuestRequestDto getGuestById(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found with id " + guestId));
        return modelMapper.map(guest, GuestRequestDto.class);
    }
}
