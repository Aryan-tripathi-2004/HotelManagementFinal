package com.example.reservationservice.controller;
import com.example.reservationservice.dto.BillResponseDto;
import com.example.reservationservice.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/all")
    public ResponseEntity<List<BillResponseDto>> getAllBills() {
        try{
            return ResponseEntity.ok(billService.getAllBills());
        } catch (Exception e) {
            System.out.println("Error fetching bills: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponseDto> getBillById(@PathVariable Long billId) {
        try{
            return ResponseEntity.ok(billService.getBillById(billId));
        } catch (Exception e) {
            System.out.println("Error fetching bill: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
