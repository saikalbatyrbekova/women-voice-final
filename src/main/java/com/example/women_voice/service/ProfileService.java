package com.example.women_voice.service;

import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    ProfileResponse updateProfile(String token, ProfileRequest profileRequest, MultipartFile file);
    ProfileResponse getProfile(String token);
    // todo: Think about update by parts
    List<ExpertResponse> getExperts(int page, int size);
}
