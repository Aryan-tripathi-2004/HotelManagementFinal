package com.example.authService.interfaces;

import com.example.authService.dtos.LoginRequestDto;
import com.example.authService.dtos.LoginResponseDto;
import com.example.authService.entities.User;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto request);

    void register(User user);
}
