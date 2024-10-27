package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;

public interface ProfileMapper {
    Profile toProfile(Profile profile, ProfileRequest request, MyFile file);
    ProfileResponse toProfileResponse(Profile profile);
}
