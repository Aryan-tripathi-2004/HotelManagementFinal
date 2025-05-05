package com.example.authService.controllers;

import com.example.authService.dtos.LoginRequestDto;
import com.example.authService.dtos.LoginResponseDto;
import com.example.authService.entities.User;
import com.example.authService.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        try{
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponseDto("Error logging in: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }
}
