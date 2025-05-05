package com.hotel.staffService.service;

import com.hotel.staffService.dto.StaffRequestDTO;
import com.hotel.staffService.dto.StaffResponseDTO;
import com.hotel.staffService.exception.ResourceNotFoundException;
import com.hotel.staffService.model.Staff;
import com.hotel.staffService.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StaffServiceImpl staffService;

    private Staff staff;
    private StaffRequestDTO staffRequestDTO;
    private StaffResponseDTO staffResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        staffRequestDTO = new StaffRequestDTO();
        staffRequestDTO.setFullName("Alice Johnson");
        staffRequestDTO.setEmail("alice@example.com");
        staffRequestDTO.setSalary(50000.0);
        staffRequestDTO.setAddress("123 Street, City");
        staffRequestDTO.setAge(30);
        staffRequestDTO.setOccupation("Manager");
        staffRequestDTO.setIdProof("Passport");
        staffRequestDTO.setIdProofNumber("ABC123456");
        staffRequestDTO.setPhoneNumber("9876543210");
        staffRequestDTO.setJoiningDate(LocalDate.parse("2024-06-01"));
        staffRequestDTO.setDepartment("Operations");

        staff = new Staff();
        staff.setFullName(staffRequestDTO.getFullName());
        staff.setEmail(staffRequestDTO.getEmail());
        staff.setSalary(staffRequestDTO.getSalary());
        staff.setAddress(staffRequestDTO.getAddress());
        staff.setAge(staffRequestDTO.getAge());
        staff.setOccupation(staffRequestDTO.getOccupation());
        staff.setIdProof(staffRequestDTO.getIdProof());
        staff.setIdProofNumber(staffRequestDTO.getIdProofNumber());
        staff.setPhoneNumber(staffRequestDTO.getPhoneNumber());
        staff.setJoiningDate(staffRequestDTO.getJoiningDate());
        staff.setDepartment(staffRequestDTO.getDepartment());

        staffResponseDTO = new StaffResponseDTO();
        staffResponseDTO.setFullName(staff.getFullName());
        staffResponseDTO.setEmail(staff.getEmail());
        staffResponseDTO.setSalary(staff.getSalary());
        staffResponseDTO.setAddress(staff.getAddress());
        staffResponseDTO.setAge(staff.getAge());
        staffResponseDTO.setOccupation(staff.getOccupation());
        staffResponseDTO.setIdProof(staff.getIdProof());
        staffResponseDTO.setIdProofNumber(staff.getIdProofNumber());
        staffResponseDTO.setPhoneNumber(staff.getPhoneNumber());
        staffResponseDTO.setJoiningDate(staff.getJoiningDate());
        staffResponseDTO.setDepartment(staff.getDepartment());
    }

    @Test
    void testCreateStaff() {
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);
        when(modelMapper.map(any(Staff.class), eq(StaffResponseDTO.class))).thenReturn(staffResponseDTO);

        StaffResponseDTO createdStaff = staffService.createStaff(staffRequestDTO);

        assertNotNull(createdStaff);
        assertEquals("Alice Johnson", createdStaff.getFullName());
        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    void testGetStaffById_Success() {
        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));
        when(modelMapper.map(any(Staff.class), eq(StaffResponseDTO.class))).thenReturn(staffResponseDTO);

        StaffResponseDTO foundStaff = staffService.getStaffById(1L);

        assertNotNull(foundStaff);
        verify(staffRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStaffById_NotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> staffService.getStaffById(1L));
        verify(staffRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllStaff() {
        when(staffRepository.findAll()).thenReturn(Arrays.asList(staff));
        when(modelMapper.map(any(Staff.class), eq(StaffResponseDTO.class))).thenReturn(staffResponseDTO);

        List<StaffResponseDTO> staffList = staffService.getAllStaff();

        assertEquals(1, staffList.size());
        verify(staffRepository, times(1)).findAll();
    }

    @Test
    void testUpdateStaff_Success() {
        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);
        when(modelMapper.map(any(Staff.class), eq(StaffResponseDTO.class))).thenReturn(staffResponseDTO);

        StaffResponseDTO updatedStaff = staffService.updateStaff(1L, staffRequestDTO);

        assertNotNull(updatedStaff);
        assertEquals("Alice Johnson", updatedStaff.getFullName());
        assertEquals("alice@example.com", updatedStaff.getEmail());
        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    void testUpdateStaff_NotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> staffService.updateStaff(1L, staffRequestDTO));
        verify(staffRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteStaff_Success() {
        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));
        doNothing().when(staffRepository).delete(any(Staff.class));

        staffService.deleteStaff(1L);

        verify(staffRepository, times(1)).delete(any(Staff.class));
    }

    @Test
    void testDeleteStaff_NotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> staffService.deleteStaff(1L));
        verify(staffRepository, times(1)).findById(1L);
    }
}