package com.example.reportService.Controller;

import com.example.reportService.Dto.*;
import com.example.reportService.feign.ReservationFeign;
import com.example.reportService.feign.RoomFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReservationFeign reservationFeign;
    
    @Autowired
    private RoomFeign roomFeign;

    @GetMapping("/getAllReservations")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return reservationFeign.getAllReservations();
    }
    
    @GetMapping("/getAllGuests")
    public ResponseEntity<List<GuestRequestDto>> getAllGuests() {
        return reservationFeign.getAllGuests();
    }
    
    @GetMapping("/getAllBills")
    public ResponseEntity<List<BillResponseDto>> getAllBills() {
        return reservationFeign.getAllBills();
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        return roomFeign.getAllRooms();
    }
    
    @GetMapping("/getAllInventories")
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories() {
        return roomFeign.getAllInventories();
    }
}
