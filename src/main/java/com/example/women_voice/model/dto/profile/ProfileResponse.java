package com.example.women_voice.model.dto.profile;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String avatarUrl;
    private String location;
    private String educationLevel;
    private String bio;
}
