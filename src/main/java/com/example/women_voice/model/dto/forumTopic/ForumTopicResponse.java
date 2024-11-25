package com.example.women_voice.model.dto.forumTopic;

import lombok.Data;

@Data
public class ForumTopicResponse {
    private Long id;
    private String author;
    private String title;
    private String description;
    private String createdAt;
    private String imagePath;
    private Integer amountPosts;
}
