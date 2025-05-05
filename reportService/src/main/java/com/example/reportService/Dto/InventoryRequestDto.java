package com.example.reportService.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventoryRequestDto {
    @NotBlank(message = "Item name must not be blank")
    private String itemName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}

