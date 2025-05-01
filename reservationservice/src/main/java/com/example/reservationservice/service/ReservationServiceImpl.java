package com.example.reservationservice.service;

import com.example.reservationservice.dto.ReservationRequestDto;
import com.example.reservationservice.dto.ReservationResponseDto;
import com.example.reservationservice.dto.RoomPriceResponseDto;
import com.example.reservationservice.entity.Bill;
import com.example.reservationservice.entity.Guest;
import com.example.reservationservice.entity.Reservation;
import com.example.reservationservice.exception.ResourceNotFoundException;
import com.example.reservationservice.feign.RoomFeignClient;
//import com.example.reservationservice.producer.EmailProducer;
import com.example.reservationservice.repository.ReservationRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomFeignClient roomFeignClient;

//    @Autowired
//    private EmailProducer emailProducer;

//    @Transactional
    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {

        // Calculate days
        int numberOfDays = requestDto.getCheckOutDate().getDayOfYear() - requestDto.getCheckInDate().getDayOfYear();
        if (numberOfDays <= 0) throw new RuntimeException("Invalid dates!");


        // Fetch Room Prices
        RoomPriceResponseDto prices = roomFeignClient.getRoomPricesByRoomNumber(requestDto.getRoomNumber());

        // Calculate Bill
        double totalAmount = prices.getFirstNightPrice() + prices.getBasicPrice() * (numberOfDays - 1);
        double tax = totalAmount * 0.18;
        double finalAmount = totalAmount + tax;

        Bill bill = Bill.builder()
                .billNumber("BILL-" + System.currentTimeMillis())
                .customerName(requestDto.getGuests().get(0).getName())
                .date(LocalDateTime.now())
                .totalAmount(totalAmount)
                .tax(tax)
                .finalAmount(finalAmount)
                .paymentMethod(requestDto.getBillRequest().getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .build();

        List<Guest> guests = requestDto.getGuests().stream()
                .map(guest -> modelMapper.map(guest, Guest.class))
                .collect(Collectors.toList());

        Reservation reservation = Reservation.builder()
                .numberOfChildren(requestDto.getNumberOfChildren())
                .numberOfAdults(requestDto.getNumberOfAdults())
                .checkInDate(requestDto.getCheckInDate())
                .checkOutDate(requestDto.getCheckOutDate())
                .roomNumber(requestDto.getRoomNumber())
                .guests(guests)
                .bill(bill)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        // Feign call to RoomService
        roomFeignClient.addReservationToRoom(prices.getRoomId(), modelMapper.map(savedReservation, ReservationResponseDto.class));

        // Send Email via RabbitMQ
//        emailProducer.sendEmail(savedReservation);

        return modelMapper.map(savedReservation, ReservationResponseDto.class);
    }

    @Override
    public ReservationResponseDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return mapToResponse(reservation);
    }

    @Override
    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto requestDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservation.setNumberOfChildren(requestDto.getNumberOfChildren());
        reservation.setNumberOfAdults(requestDto.getNumberOfAdults());
        reservation.setCheckInDate(requestDto.getCheckInDate());
        reservation.setCheckOutDate(requestDto.getCheckOutDate());
        reservation.setRoomNumber(requestDto.getRoomNumber());

        List<Guest> guests = requestDto.getGuests().stream()
                .map(guestDto -> modelMapper.map(guestDto, Guest.class))
                .collect(Collectors.toList());

        reservation.setGuests(guests);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToResponse(updatedReservation);
    }

    private ReservationResponseDto mapToResponse(Reservation reservation) {
        ReservationResponseDto response = modelMapper.map(reservation, ReservationResponseDto.class);
        response.setGuestNames(
                reservation.getGuests().stream()
                        .map(Guest::getName)
                        .collect(Collectors.toList())
        );
        response.setMessage("Reservation successful");
        return response;
    }
}
