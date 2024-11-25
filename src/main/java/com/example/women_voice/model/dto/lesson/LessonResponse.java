package com.example.women_voice.model.dto.lesson;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonResponse {
    private String author;
    private String course;
    private String title;
    private String content;
    private String videoUrl;
    private LocalDateTime createdAt;
}
