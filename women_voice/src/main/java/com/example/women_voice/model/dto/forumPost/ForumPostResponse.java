package com.example.women_voice.model.dto.forumPost;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumPostResponse {
    private Long id;
    private String author;
    private String topic;
    private String content;
    private LocalDateTime createdAt;
}
