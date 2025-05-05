package com.example.authService.services;

import com.example.authService.configs.JwtUtil;
import com.example.authService.dtos.LoginRequestDto;
import com.example.authService.dtos.LoginResponseDto;
import com.example.authService.entities.User;
import com.example.authService.exceptions.UserNotFoundException;
import com.example.authService.interfaces.IAuthService;
import com.example.authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        System.out.println(user.getRole());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(),user.getRole());
        return new LoginResponseDto(token);
    }

    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
