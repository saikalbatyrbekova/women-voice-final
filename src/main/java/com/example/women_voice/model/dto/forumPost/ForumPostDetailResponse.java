package com.example.women_voice.model.dto.forumPost;

import lombok.Data;

@Data
public class ForumPostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String imageUrl;
    private AuthorResponse author;
}
