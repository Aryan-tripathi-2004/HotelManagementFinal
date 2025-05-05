package com.example.roomservice.service;

import com.example.roomservice.dto.InventoryRequestDto;
import com.example.roomservice.dto.InventoryResponseDto;
import com.example.roomservice.entity.Inventory;
import com.example.roomservice.entity.Room;
import com.example.roomservice.repository.InventoryRepository;
import com.example.roomservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventories() {
        Inventory inventory = new Inventory();
        InventoryResponseDto responseDto = new InventoryResponseDto();

        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));
        when(modelMapper.map(inventory, InventoryResponseDto.class)).thenReturn(responseDto);

        List<InventoryResponseDto> result = inventoryService.getAllInventories();

        assertEquals(1, result.size());
        verify(inventoryRepository).findAll();
    }

    @Test
    void testGetInventoryById() {
        Inventory inventory = new Inventory();
        InventoryResponseDto responseDto = new InventoryResponseDto();

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(modelMapper.map(inventory, InventoryResponseDto.class)).thenReturn(responseDto);

        InventoryResponseDto result = inventoryService.getInventoryById(1L);

        assertNotNull(result);
        verify(inventoryRepository).findById(1L);
    }

    @Test
    void testGetInventoryByIdNotFound() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventoryService.getInventoryById(1L));
    }

    @Test
    void testAddInventory() {
        Long roomId = 1L;
        Room room = new Room();
        room.setInventories(new ArrayList<>());

        InventoryRequestDto dto = new InventoryRequestDto();
        Inventory inventory = new Inventory();
        InventoryResponseDto responseDto = new InventoryResponseDto();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(modelMapper.map(dto, Inventory.class)).thenReturn(inventory);
        when(modelMapper.map(inventory, InventoryResponseDto.class)).thenReturn(responseDto);
        when(roomRepository.save(room)).thenReturn(room);

        InventoryResponseDto result = inventoryService.addInventory(roomId, dto);

        assertNotNull(result);
        assertEquals(1, room.getInventories().size());
        verify(roomRepository).save(room);
    }

    @Test
    void testAddInventoryRoomNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventoryService.addInventory(1L, new InventoryRequestDto()));
    }

    @Test
    void testUpdateInventory() {
        Long inventoryId = 1L;
        InventoryRequestDto dto = new InventoryRequestDto();
        dto.setItemName("Chair");
        dto.setQuantity(5);

        Inventory inventory = new Inventory();
        Inventory updatedInventory = new Inventory();
        InventoryResponseDto responseDto = new InventoryResponseDto();

        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(inventory)).thenReturn(updatedInventory);
        when(modelMapper.map(updatedInventory, InventoryResponseDto.class)).thenReturn(responseDto);

        InventoryResponseDto result = inventoryService.updateInventory(inventoryId, dto);

        assertNotNull(result);
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testUpdateInventoryNotFound() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventoryService.updateInventory(1L, new InventoryRequestDto()));
    }

    @Test
    void testDeleteInventory() {
        inventoryService.deleteInventory(1L);
        verify(inventoryRepository).deleteById(1L);
    }
}

