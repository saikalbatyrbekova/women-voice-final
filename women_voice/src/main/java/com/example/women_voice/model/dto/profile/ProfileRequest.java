package com.example.women_voice.model.dto.profile;

import lombok.Data;

@Data
public class ProfileRequest {
    private String fullName;
    private String bio;
    private String location;
    private String educationLevel;
}
