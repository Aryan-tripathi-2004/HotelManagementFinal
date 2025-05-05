package com.example.roomservice.service;

import com.example.roomservice.dto.InventoryRequestDto;
import com.example.roomservice.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDto> getAllInventories();
    InventoryResponseDto getInventoryById(Long id);
    InventoryResponseDto addInventory(Long roomId, InventoryRequestDto dto);
    InventoryResponseDto updateInventory(Long id, InventoryRequestDto dto);
    void deleteInventory(Long id);
}

