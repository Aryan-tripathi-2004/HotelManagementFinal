package com.example.reservationservice.service;

import com.example.reservationservice.dto.BillResponseDto;
import java.util.List;

public interface BillService {
    List<BillResponseDto> getAllBills();

    BillResponseDto getBillById(Long billId);
}
