package com.example.roomservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long InventoryId;

    private String itemName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;
}

