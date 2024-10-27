package com.example.women_voice.service.impl;

import com.example.women_voice.config.JwtService;
import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.AuthMapper;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.auth.AuthResponse;
import com.example.women_voice.model.dto.auth.LoginRequest;
import com.example.women_voice.model.dto.auth.RegisterRequest;
import com.example.women_voice.repository.ProfileRepository;
import com.example.women_voice.repository.UserRepository;
import com.example.women_voice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User is already contains", HttpStatus.BAD_REQUEST);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userRepository.save(authMapper.toUser(request));
        String token = jwtService.generateToken(user);
        Profile profile = new Profile();
        profile.setUser(user);
        profileRepository.save(profile);
        return authMapper.toAuthResponse(user.getId(), token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return authMapper.toAuthResponse(user.getId(), jwtService.generateToken(user));
    }

    @Override
    public User getUserFromToken(String token) {
        token = token.substring(7);
        String email = jwtService.getUserEmailFromToken(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }
}
