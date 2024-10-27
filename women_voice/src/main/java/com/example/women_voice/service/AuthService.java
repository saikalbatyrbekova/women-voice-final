package com.example.women_voice.service;

import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.auth.AuthResponse;
import com.example.women_voice.model.dto.auth.LoginRequest;
import com.example.women_voice.model.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    User getUserFromToken(String token);
}
