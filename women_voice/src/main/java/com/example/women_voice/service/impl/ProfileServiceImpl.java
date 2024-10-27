package com.example.women_voice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ProfileMapper;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import com.example.women_voice.repository.ProfileRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final AuthService authService;
    private final AmazonService amazonService;

    @Override
    public ProfileResponse updateProfile(String token, ProfileRequest profileRequest, MultipartFile file) {
        User user = authService.getUserFromToken(token);
        Profile profile = profileMapper.toProfile(profileRepository.findByUser(user).orElseThrow(() -> new CustomException("Profile not found", HttpStatus.NOT_FOUND)), profileRequest, amazonService.uploadFile(file));
        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    @Override
    public ProfileResponse getProfile(String token) {
        User user = authService.getUserFromToken(token);
        return profileMapper.toProfileResponse(profileRepository.findByUser(user).orElseThrow(() -> new CustomException("Profile not found", HttpStatus.NOT_FOUND)));
    }
}
