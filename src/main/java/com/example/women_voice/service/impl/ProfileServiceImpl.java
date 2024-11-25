package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ProfileMapper;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import com.example.women_voice.model.enums.Role;
import com.example.women_voice.repository.ProfileRepository;
import com.example.women_voice.repository.UserRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
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

    @Override
    public List<ExpertResponse> getExperts(int page, int size) {
        List<Profile> profileList = userRepository.findAllByRole(Role.EXPERT, PageRequest.of(page, size))
                .stream()
                .map(User::getProfile)
                .filter(Objects::nonNull)
                .toList();

        return profileMapper.toExpertResponseList(profileList);
    }
}
