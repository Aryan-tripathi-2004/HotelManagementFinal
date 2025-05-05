package com.example.roomservice.controller;

import com.example.roomservice.dto.InventoryRequestDto;
import com.example.roomservice.dto.InventoryResponseDto;
import com.example.roomservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public ResponseEntity<List<InventoryResponseDto>>getAllInventories() {
        try {
            return ResponseEntity.ok(inventoryService.getAllInventories());
        } catch (Exception e) {
            System.out.println("Error fetching inventories: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping("/{InventoryId}")
    public ResponseEntity<InventoryResponseDto> getById(@PathVariable Long InventoryId) {
        try {
            return ResponseEntity.ok(inventoryService.getInventoryById(InventoryId));
        } catch (Exception e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{roomId}/add")
    public ResponseEntity<InventoryResponseDto> add(@PathVariable Long roomId,
                                                    @Valid @RequestBody InventoryRequestDto dto) {
        try {
            return ResponseEntity.ok(inventoryService.addInventory(roomId, dto));
        } catch (Exception e) {
            System.out.println("Error adding inventory: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{InventoryId}")
    public ResponseEntity<InventoryResponseDto> update(@PathVariable Long InventoryId,
                                                       @Valid @RequestBody InventoryRequestDto dto) {
        try {
            return ResponseEntity.ok(inventoryService.updateInventory(InventoryId, dto));
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{InventoryId}")
    public void delete(@PathVariable Long InventoryId) {
        try {
            inventoryService.getInventoryById(InventoryId);
        } catch (Exception e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
            return;
        }
    }
}

