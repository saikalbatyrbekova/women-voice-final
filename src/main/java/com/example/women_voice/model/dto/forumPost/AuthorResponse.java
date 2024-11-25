package com.example.women_voice.model.dto.forumPost;

import lombok.Data;

@Data
public class AuthorResponse {
    private Long id;
    private String imagePath;
    private String fullName;
    private String bio;
}
