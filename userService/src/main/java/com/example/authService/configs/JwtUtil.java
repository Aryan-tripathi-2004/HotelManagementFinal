package com.example.authService.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("nVX7PbKwO5NsMwFQWxIyQJZ3ZtE3Z2m9IV3TrxTiMI4=".getBytes());

    public String generateToken(String username, String role) {
        System.out.println(role);
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Ensure role is included
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SECRET_KEY)
                .compact();

    }

}
