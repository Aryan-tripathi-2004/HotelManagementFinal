package com.example.authService.service;


import com.example.authService.configs.JwtUtil;
import com.example.authService.dtos.LoginRequestDto;
import com.example.authService.dtos.LoginResponseDto;
import com.example.authService.entities.User;
import com.example.authService.exceptions.UserNotFoundException;
import com.example.authService.repository.UserRepository;
import com.example.authService.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("john");
        testUser.setPassword("encodedPass");
        testUser.setRole("USER");
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        LoginRequestDto request = new LoginRequestDto("john", "password");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
        when(jwtUtil.generateToken("john", "USER")).thenReturn("fake-jwt-token");

        LoginResponseDto response = authService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        LoginRequestDto request = new LoginRequestDto("invalid", "pass");

        assertThrows(UserNotFoundException.class, () -> authService.login(request));
    }

    @Test
    void login_shouldThrowException_whenPasswordInvalid() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpass", "encodedPass")).thenReturn(false);

        LoginRequestDto request = new LoginRequestDto("john", "wrongpass");

        assertThrows(UserNotFoundException.class, () -> authService.login(request));
    }

    @Test
    void register_shouldSaveEncodedPassword() {
        User newUser = new User();
        newUser.setUsername("alice");
        newUser.setPassword("rawPassword");
        newUser.setRole("ADMIN");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        authService.register(newUser);

        assertEquals("encodedPassword", newUser.getPassword());
        verify(userRepository, times(1)).save(newUser);
    }
}
