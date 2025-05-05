package com.example.roomservice.controller;

import com.example.roomservice.dto.ReservationInputDto;
import com.example.roomservice.dto.RoomPriceResponseDto;
import com.example.roomservice.dto.RoomRequestDto;
import com.example.roomservice.dto.RoomResponseDto;
import com.example.roomservice.entity.Room;
import com.example.roomservice.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@Valid @RequestBody RoomRequestDto roomRequestDto) {
        try{
            return ResponseEntity.ok(roomService.createRoom(roomRequestDto));
        } catch (Exception e) {
            System.out.println("Error creating room: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(@PathVariable Long id,
                                                      @Valid @RequestBody RoomRequestDto roomRequestDto) {
        try{
        return ResponseEntity.ok(roomService.updateRoom(id, roomRequestDto));
        } catch (Exception e) {
            System.out.println("Error updating room: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try{
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("Error deleting room: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        try{
        return ResponseEntity.ok(roomService.getAllRooms());
        }
        catch (Exception e) {
            System.out.println("Error fetching all rooms: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) {
        try{
        return ResponseEntity.ok(roomService.getRoomById(id));
        } catch (Exception e) {
            System.out.println("Error fetching room by ID: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{roomId}/add-reservation")
    public ResponseEntity<String> addReservationToRoom(@PathVariable Long roomId,
                                                       @Valid @RequestBody ReservationInputDto reservationInputDto) {
        try {
            roomService.addReservationToRoom(roomId, reservationInputDto);
            return ResponseEntity.ok("Reservation added successfully to room ID: " + roomId);
        } catch (Exception e) {
            System.out.println("Error adding reservation to room: " + e.getMessage());
            return ResponseEntity.badRequest().body("Room not found with ID: " + roomId);
        }

    }

    @PostMapping("/available")
    public ResponseEntity<List<RoomResponseDto>> getAvailableRooms(@RequestParam String inputDate) {
        try {
            LocalDate date = LocalDate.parse(inputDate);
            return ResponseEntity.ok(roomService.getAvailableRoomsByDate(date));
        } catch (Exception e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/roomNumber/{roomNumber}")
    public ResponseEntity<RoomPriceResponseDto> getRoomPricesByRoomNumber(@PathVariable("roomNumber") String roomNumber) {
        try{
        RoomPriceResponseDto room = roomService.getRoomPrices(roomNumber);
        return ResponseEntity.ok(room);
        } catch (Exception e) {
            System.out.println("Error fetching room prices: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
