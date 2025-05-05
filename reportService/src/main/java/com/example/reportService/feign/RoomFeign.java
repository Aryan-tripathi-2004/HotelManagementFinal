package com.example.reportService.feign;

import com.example.reportService.Dto.InventoryResponseDto;
import com.example.reportService.Dto.RoomResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomFeign {

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms();

    @GetMapping("/all")
    public ResponseEntity<List<InventoryResponseDto>>getAllInventories();
}
