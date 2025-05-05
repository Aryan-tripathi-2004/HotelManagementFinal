package com.example.reservationservice.controller;

import com.example.reservationservice.dto.ReservationRequestDto;
import com.example.reservationservice.dto.ReservationResponseDto;
import com.example.reservationservice.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody ReservationRequestDto requestDto) {
       try {
           return ResponseEntity.ok(reservationService.createReservation(requestDto));
        } catch (Exception e) {
            System.out.println("Error creating reservation: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.getReservationById(id));
        } catch (Exception e) {
            System.out.println("Error fetching reservation: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        try {
            return ResponseEntity.ok(reservationService.getAllReservations());
        } catch (Exception e) {
            System.out.println("Error fetching all reservations: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id,
                                                                    @Valid @RequestBody ReservationRequestDto requestDto) {
        try {
            return ResponseEntity.ok(reservationService.updateReservation(id, requestDto));
        } catch (Exception e) {
            System.out.println("Error updating reservation: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (Exception e) {
            System.out.println("Error fetching reservation: " + e.getMessage());
            return ResponseEntity.badRequest().body("Reservation not found");
        }

    }
}
