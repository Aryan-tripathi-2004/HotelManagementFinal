package com.example.reservationservice.service;

import com.example.reservationservice.dto.BillResponseDto;
import com.example.reservationservice.entity.Bill;
import com.example.reservationservice.repository.BillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService{

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BillResponseDto> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        List<BillResponseDto> billResponseDtos = bills.stream()
                .map(bill -> modelMapper.map(bill, BillResponseDto.class))
                .toList();

        return billResponseDtos;
    }

    @Override
    public BillResponseDto getBillById(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id " + billId));
        return modelMapper.map(bill, BillResponseDto.class);
    }
}
