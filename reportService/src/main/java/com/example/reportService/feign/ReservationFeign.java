package com.example.reportService.feign;

import com.example.reportService.Dto.BillResponseDto;
import com.example.reportService.Dto.GuestRequestDto;
import com.example.reportService.Dto.ReservationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationFeign {

    @GetMapping("/api/reservations")
    ResponseEntity<List<ReservationResponseDto>> getAllReservations();

    @GetMapping("/all")
    public ResponseEntity<List<GuestRequestDto>> getAllGuests();

    @GetMapping("/all")
    public ResponseEntity<List<BillResponseDto>> getAllBills();

}
