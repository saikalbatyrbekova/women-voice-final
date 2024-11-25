package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.AuthMapper;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.auth.AuthResponse;
import com.example.women_voice.model.dto.auth.RegisterRequest;
import com.example.women_voice.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthMapperImpl implements AuthMapper {
    @Override
    public User toUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole()));
        return user;
    }

    @Override
    public AuthResponse toAuthResponse(Long id, String token) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(id);
        authResponse.setAccess_token(token);
        return authResponse;
    }
}
