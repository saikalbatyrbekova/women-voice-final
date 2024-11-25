package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.auth.AuthResponse;
import com.example.women_voice.model.dto.auth.RegisterRequest;

public interface AuthMapper {
    User toUser(RegisterRequest request);
    AuthResponse toAuthResponse(Long id, String token);
}
