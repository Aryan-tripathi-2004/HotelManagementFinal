package com.example.authService.interfaces;

import com.example.authService.dtos.LoginRequestDto;
import com.example.authService.dtos.LoginResponseDto;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto request);
}
