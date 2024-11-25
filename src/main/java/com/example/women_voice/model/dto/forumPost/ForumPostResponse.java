package com.example.women_voice.model.dto.forumPost;

import lombok.Data;

@Data
public class ForumPostResponse {
    private Long id;
    private String title;
    private String createdAt;
    private String imageUrl;
}
