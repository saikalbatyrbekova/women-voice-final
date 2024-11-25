package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.ProfileMapper;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapperImpl implements ProfileMapper {
    @Override
    public Profile toProfile(Profile profile, ProfileRequest request, MyFile file) {
        profile.setFullName(request.getFullName());
        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setEducationLevel(request.getEducationLevel());
        profile.setImage(file);
        return profile;
    }

    @Override
    public ProfileResponse toProfileResponse(Profile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setId(profile.getId());
        response.setFullName(profile.getFullName());
        response.setUsername(profile.getUser().getUsername());
        response.setEmail(profile.getUser().getEmail());
        response.setPassword(profile.getUser().getPassword());
        response.setAvatarUrl(profile.getImage().getPath());
        response.setLocation(profile.getLocation());
        response.setEducationLevel(profile.getEducationLevel());
        response.setBio(profile.getBio());
        return response;
    }

    @Override
    public ExpertResponse toExpertResponse(Profile profile) {
        ExpertResponse response = new ExpertResponse();
        response.setName(profile.getFullName());
        response.setImagePath(profile.getImage().getPath());
        response.setEducationalLevel(profile.getEducationLevel());
        return response;
    }

    @Override
    public List<ExpertResponse> toExpertResponseList(List<Profile> profiles) {
        List<ExpertResponse> expertResponses = new ArrayList<>();
        for (Profile profile : profiles) {
            expertResponses.add(toExpertResponse(profile));
        }
        return expertResponses;
    }
}
