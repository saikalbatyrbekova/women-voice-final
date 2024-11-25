package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;

import java.util.List;

public interface ProfileMapper {
    Profile toProfile(Profile profile, ProfileRequest request, MyFile file);
    ProfileResponse toProfileResponse(Profile profile);
    ExpertResponse toExpertResponse(Profile profile);
    List<ExpertResponse> toExpertResponseList(List<Profile> profiles);
}
