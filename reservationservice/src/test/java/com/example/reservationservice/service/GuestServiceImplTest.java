package com.example.reservationservice.service;

import com.example.reservationservice.dto.GuestRequestDto;
import com.example.reservationservice.entity.Guest;
import com.example.reservationservice.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private GuestServiceImpl guestService;

    private Guest guest;

    @BeforeEach
    void setup() {
        guest = new Guest();
        guest.setGuestId(1L);
        guest.setName("John Doe");
        guest.setEmail("john@example.com");
        guest.setPhoneNumber("1234567890");
    }

    @Test
    void getAllGuests_shouldReturnList() {
        when(guestRepository.findAll()).thenReturn(List.of(guest));

        List<GuestRequestDto> result = guestService.getAllGuests();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getAllGuests_shouldThrowWhenEmpty() {
        when(guestRepository.findAll()).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> guestService.getAllGuests());
        assertEquals("No guests found", ex.getMessage());
    }

    @Test
    void getGuestById_shouldReturnGuest() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        GuestRequestDto result = guestService.getGuestById(1L);

        assertEquals("John Doe", result.getName());
    }

    @Test
    void getGuestById_shouldThrowIfNotFound() {
        when(guestRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> guestService.getGuestById(99L));
        assertEquals("Guest not found with id 99", ex.getMessage());
    }
}

