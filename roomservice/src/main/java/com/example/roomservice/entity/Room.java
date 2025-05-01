package com.example.roomservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private double basicPrice;

    private double firstNightPrice;

    private int capacity;

    @ElementCollection
    @CollectionTable(name = "room_reservations", joinColumns = @JoinColumn(name = "roomId"))
    private List<Reservation> reservations;
}
