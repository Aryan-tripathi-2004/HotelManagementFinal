package com.example.roomservice.service;

import com.example.roomservice.dto.InventoryRequestDto;
import com.example.roomservice.dto.InventoryResponseDto;
import com.example.roomservice.entity.Inventory;
import com.example.roomservice.entity.Room;
import com.example.roomservice.repository.InventoryRepository;
import com.example.roomservice.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<InventoryResponseDto> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inv -> modelMapper.map(inv, InventoryResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDto getInventoryById(Long InventoryId) {
        Inventory inv = inventoryRepository.findById(InventoryId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        return modelMapper.map(inv, InventoryResponseDto.class);
    }

    @Override
    public InventoryResponseDto addInventory(Long roomId, InventoryRequestDto dto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Inventory inv = modelMapper.map(dto, Inventory.class);
        inv.setRoom(room);  // Set the room for the inventory

        room.getInventories().add(inv);
        roomRepository.save(room);  // Persist both room and inventory

        return modelMapper.map(inv, InventoryResponseDto.class);
    }

    @Override
    public InventoryResponseDto updateInventory(Long InventoryId, InventoryRequestDto dto) {
        Inventory inv = inventoryRepository.findById(InventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inv.setItemName(dto.getItemName());
        inv.setQuantity(dto.getQuantity());
        return modelMapper.map(inventoryRepository.save(inv), InventoryResponseDto.class);
    }

    @Override
    public void deleteInventory(Long InventoryId) {
        inventoryRepository.deleteById(InventoryId);
    }
}
