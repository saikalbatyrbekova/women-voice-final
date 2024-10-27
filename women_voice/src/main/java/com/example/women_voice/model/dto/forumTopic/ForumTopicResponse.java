package com.example.women_voice.model.dto.forumTopic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForumTopicResponse {
    private String author;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
