package com.example.roomservice.service;

import com.example.roomservice.dto.ReservationInputDto;
import com.example.roomservice.dto.RoomPriceResponseDto;
import com.example.roomservice.dto.RoomRequestDto;
import com.example.roomservice.dto.RoomResponseDto;
import com.example.roomservice.entity.Reservation;
import com.example.roomservice.entity.Room;
import com.example.roomservice.exception.ResourceNotFoundException;
import com.example.roomservice.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        Room room = modelMapper.map(roomRequestDto, Room.class);
        Room savedRoom = roomRepository.save(room);
        return modelMapper.map(savedRoom, RoomResponseDto.class);
    }

    @Override
    public RoomResponseDto updateRoom(Long id, RoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));

        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setBasicPrice(roomRequestDto.getBasicPrice());
        room.setFirstNightPrice(roomRequestDto.getFirstNightPrice());
        room.setCapacity(roomRequestDto.getCapacity());

        Room updatedRoom = roomRepository.save(room);
        return modelMapper.map(updatedRoom, RoomResponseDto.class);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        roomRepository.delete(room);
    }

    @Override
    public List<RoomResponseDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> modelMapper.map(room, RoomResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        return modelMapper.map(room, RoomResponseDto.class);
    }

    @Override
    public void addReservationToRoom(Long roomId, ReservationInputDto reservationInputDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomId));

        Reservation newReservation = Reservation.builder()
                .reservationId(reservationInputDto.getReservationId())
                .checkInDate(reservationInputDto.getCheckInDate())
                .checkOutDate(reservationInputDto.getCheckOutDate())
                .numberOfChildren(reservationInputDto.getNumberOfChildren())
                .numberOfAdults(reservationInputDto.getNumberOfAdults())
                .build();

        room.getReservations().add(newReservation);
        roomRepository.save(room);
    }

    @Override
    public List<RoomResponseDto> getAvailableRoomsByDate(LocalDate date) {
        List<Room> rooms = roomRepository.findAll();
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean isAvailable = room.getReservations().stream()
                    .noneMatch(reservation ->
                            !(date.isBefore(reservation.getCheckInDate()) || date.isAfter(reservation.getCheckOutDate()))
                    );
            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms.stream()
                .map(room -> modelMapper.map(room, RoomResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomPriceResponseDto getRoomPrices(String roomNumber) {
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        if (room.isEmpty()) {
            throw new ResourceNotFoundException("Room not found with room number " + roomNumber);
        }

        Room roomEntity = room.get();
        return RoomPriceResponseDto.builder()
                .roomId(roomEntity.getRoomId())
                .basicPrice(roomEntity.getBasicPrice())
                .firstNightPrice(roomEntity.getFirstNightPrice())
                .build();
    }


}
