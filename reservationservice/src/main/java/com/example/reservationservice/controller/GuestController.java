package com.example.reservationservice.controller;

import com.example.reservationservice.dto.GuestRequestDto;
import com.example.reservationservice.entity.Guest;
import com.example.reservationservice.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/all")
    public ResponseEntity<List<GuestRequestDto>> getAllGuests() {
        try {
            List<GuestRequestDto> guests = guestService.getAllGuests();
            return ResponseEntity.ok(guests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{GuestId}")
    public ResponseEntity<GuestRequestDto> getGuestById(@PathVariable Long GuestId) {
        try {
            GuestRequestDto guest = guestService.getGuestById(GuestId);
            return ResponseEntity.ok(guest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
