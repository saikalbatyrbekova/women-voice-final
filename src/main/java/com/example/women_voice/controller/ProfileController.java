package com.example.women_voice.controller;

import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import com.example.women_voice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PutMapping("/update")
    public ProfileResponse createProfile(
            @RequestHeader("Authorization") String token,
            @RequestPart(name = "request") ProfileRequest profileRequest,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return profileService.updateProfile(token, profileRequest, multipartFile);
    }

    @GetMapping
    public ProfileResponse getProfile(@RequestHeader("Authorization") String token) {
        return profileService.getProfile(token);
    }

    @GetMapping("/experts")
    public List<ExpertResponse> getExperts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return profileService.getExperts(page, size);
    }
}
