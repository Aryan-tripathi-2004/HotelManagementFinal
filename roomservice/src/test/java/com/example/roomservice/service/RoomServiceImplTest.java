package com.example.roomservice.service;

import com.example.roomservice.dto.*;
import com.example.roomservice.entity.Reservation;
import com.example.roomservice.entity.Room;
import com.example.roomservice.exception.ResourceNotFoundException;
import com.example.roomservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom() {
        RoomRequestDto dto = new RoomRequestDto();
        Room room = new Room();
        Room savedRoom = new Room();
        RoomResponseDto responseDto = new RoomResponseDto();

        when(modelMapper.map(dto, Room.class)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(savedRoom);
        when(modelMapper.map(savedRoom, RoomResponseDto.class)).thenReturn(responseDto);

        RoomResponseDto result = roomService.createRoom(dto);

        assertNotNull(result);
        verify(roomRepository).save(room);
    }

    @Test
    void testUpdateRoom() {
        Long id = 1L;
        RoomRequestDto dto = new RoomRequestDto();
        Room room = new Room();
        Room updatedRoom = new Room();
        RoomResponseDto responseDto = new RoomResponseDto();

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(updatedRoom);
        when(modelMapper.map(updatedRoom, RoomResponseDto.class)).thenReturn(responseDto);

        RoomResponseDto result = roomService.updateRoom(id, dto);

        assertNotNull(result);
        verify(roomRepository).save(room);
    }

    @Test
    void testUpdateRoomNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.updateRoom(1L, new RoomRequestDto()));
    }

    @Test
    void testDeleteRoom() {
        Room room = new Room();
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        roomService.deleteRoom(1L);
        verify(roomRepository).delete(room);
    }

    @Test
    void testDeleteRoomNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.deleteRoom(1L));
    }

    @Test
    void testGetAllRooms() {
        Room room = new Room();
        RoomResponseDto dto = new RoomResponseDto();
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(modelMapper.map(room, RoomResponseDto.class)).thenReturn(dto);

        List<RoomResponseDto> rooms = roomService.getAllRooms();

        assertEquals(1, rooms.size());
    }

    @Test
    void testGetRoomById() {
        Room room = new Room();
        RoomResponseDto dto = new RoomResponseDto();
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(modelMapper.map(room, RoomResponseDto.class)).thenReturn(dto);

        RoomResponseDto result = roomService.getRoomById(1L);

        assertNotNull(result);
    }

    @Test
    void testGetRoomByIdNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomById(1L));
    }

    @Test
    void testAddReservationToRoom() {
        Room room = new Room();
        room.setReservations(new ArrayList<>());
        room.setRoomNumber("101");

        ReservationInputDto dto = ReservationInputDto.builder()
                .reservationId(1L)
                .checkInDate(LocalDate.of(2025, 5, 1))
                .checkOutDate(LocalDate.of(2025, 5, 3))
                .numberOfAdults(2)
                .numberOfChildren(1)
                .build();

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        roomService.addReservationToRoom(1L, dto);

        verify(roomRepository).save(any(Room.class));
        assertEquals(1, room.getReservations().size());
    }

    @Test
    void testGetAvailableRoomsByDate() {
        Room room1 = new Room();
        room1.setReservations(new ArrayList<>());

        Room room2 = new Room();
        Reservation r = Reservation.builder()
                .checkInDate(LocalDate.of(2025, 5, 10))
                .checkOutDate(LocalDate.of(2025, 5, 12))
                .build();
        room2.setReservations(List.of(r));

        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(modelMapper.map(any(), eq(RoomResponseDto.class))).thenReturn(new RoomResponseDto());

        List<RoomResponseDto> result = roomService.getAvailableRoomsByDate(LocalDate.of(2025, 5, 5));

        assertEquals(2, result.size());
    }

    @Test
    void testGetRoomPrices() {
        Room room = new Room();
        room.setRoomId(1L);
        room.setBasicPrice(100.0);
        room.setFirstNightPrice(150.0);

        when(roomRepository.findByRoomNumber("101")).thenReturn(Optional.of(room));

        RoomPriceResponseDto result = roomService.getRoomPrices("101");

        assertEquals(1L, result.getRoomId());
        assertEquals(100.0, result.getBasicPrice());
        assertEquals(150.0, result.getFirstNightPrice());
    }

    @Test
    void testGetRoomPricesNotFound() {
        when(roomRepository.findByRoomNumber("999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomPrices("999"));
    }
}
