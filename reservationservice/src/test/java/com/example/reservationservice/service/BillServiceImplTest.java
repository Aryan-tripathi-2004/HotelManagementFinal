package com.example.reservationservice.service;

import com.example.reservationservice.dto.BillResponseDto;
import com.example.reservationservice.entity.Bill;
import com.example.reservationservice.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private BillServiceImpl billService;

    private Bill bill;

    @BeforeEach
    void setUp() {
        bill = Bill.builder()
                .billId(1L)
                .billNumber("BILL-001")
                .customerName("Alice")
                .totalAmount(1000.0)
                .tax(180.0)
                .finalAmount(1180.0)
                .paymentMethod("CARD")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllBills_shouldReturnListOfBills() {
        when(billRepository.findAll()).thenReturn(List.of(bill));

        List<BillResponseDto> result = billService.getAllBills();

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getCustomerName());
    }

    @Test
    void getBillById_shouldReturnBill() {
        when(billRepository.findById(1L)).thenReturn(Optional.of(bill));

        BillResponseDto result = billService.getBillById(1L);

        assertEquals("Alice", result.getCustomerName());
        assertEquals(1180.0, result.getFinalAmount());
    }

    @Test
    void getBillById_shouldThrowWhenNotFound() {
        when(billRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> billService.getBillById(99L));
        assertEquals("Bill not found with id 99", ex.getMessage());
    }
}

