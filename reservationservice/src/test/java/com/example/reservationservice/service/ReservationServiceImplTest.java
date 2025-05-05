package com.example.reservationservice.service;

import com.example.reservationservice.dto.*;
import com.example.reservationservice.entity.*;
import com.example.reservationservice.exception.ResourceNotFoundException;
import com.example.reservationservice.feign.RoomFeignClient;
import com.example.reservationservice.repository.BillRepository;
import com.example.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomFeignClient roomFeignClient;

    @Mock
    private BillRepository billRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationRequestDto requestDto;

    @BeforeEach
    void setup() {
        GuestRequestDto guestDto = new GuestRequestDto();
        guestDto.setName("John");

        BillRequestDto billRequestDto = new BillRequestDto();
        billRequestDto.setPaymentMethod("UPI");

        requestDto = new ReservationRequestDto();
        requestDto.setNumberOfAdults(2);
        requestDto.setNumberOfChildren(1);
        requestDto.setCheckInDate(LocalDate.of(2025, 5, 10));
        requestDto.setCheckOutDate(LocalDate.of(2025, 5, 12));
        requestDto.setRoomNumber("A101");
        requestDto.setGuests(List.of(guestDto));
        requestDto.setBillRequest(billRequestDto);
    }

    @Test
    void createReservation_shouldSucceed() {
        RoomPriceResponseDto roomPrice = RoomPriceResponseDto.builder()
                .roomId(1L)
                .firstNightPrice(100)
                .basicPrice(50)
                .build();

        when(roomFeignClient.getRoomPricesByRoomNumber("A101")).thenReturn(roomPrice);

        Reservation saved = Reservation.builder()
                .reservationId(1L)
                .roomNumber("A101")
                .checkInDate(requestDto.getCheckInDate())
                .checkOutDate(requestDto.getCheckOutDate())
                .numberOfAdults(2)
                .numberOfChildren(1)
                .guests(List.of(Guest.builder().name("John").build()))
                .bill(Bill.builder().customerName("John").build())
                .build();

        when(reservationRepository.save(any())).thenReturn(saved);

        ReservationResponseDto result = reservationService.createReservation(requestDto);

        assertEquals("A101", result.getRoomNumber());
        assertEquals(2, result.getNumberOfAdults());
        verify(roomFeignClient).addReservationToRoom(eq(1L), any());
    }

    @Test
    void getReservationById_shouldReturnReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setRoomNumber("A102");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        ReservationResponseDto result = reservationService.getReservationById(1L);
        assertEquals("A102", result.getRoomNumber());
    }

    @Test
    void getReservationById_shouldThrowIfNotFound() {
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservationService.getReservationById(999L));
    }

    @Test
    void getAllReservations_shouldReturnList() {
        Reservation r1 = new Reservation();
        r1.setReservationId(1L);
        r1.setRoomNumber("A1");

        Reservation r2 = new Reservation();
        r2.setReservationId(2L);
        r2.setRoomNumber("A2");

        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        List<ReservationResponseDto> results = reservationService.getAllReservations();
        assertEquals(2, results.size());
    }

    @Test
    void deleteReservation_shouldSucceed() {
        Reservation r = new Reservation();
        r.setReservationId(1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(r));

        reservationService.deleteReservation(1L);
        verify(reservationRepository).delete(r);
    }

    @Test
    void updateReservation_shouldSucceed() {
        Reservation existing = new Reservation();
        existing.setReservationId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existing));

        GuestRequestDto guestDto = new GuestRequestDto();
        guestDto.setName("Updated Guest");
        requestDto.setGuests(List.of(guestDto));
        requestDto.setRoomNumber("B202");

        Reservation updated = new Reservation();
        updated.setReservationId(1L);
        updated.setRoomNumber("B202");

        when(reservationRepository.save(any())).thenReturn(updated);

        ReservationResponseDto result = reservationService.updateReservation(1L, requestDto);
        assertEquals("B202", result.getRoomNumber());
    }
}
