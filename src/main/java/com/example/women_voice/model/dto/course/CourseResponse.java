package com.example.women_voice.model.dto.course;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String imagePath;
    private String description;
    private String category;
    private String createdBy;
    private LocalDateTime createdAt;
    private Integer amountLessons;
}
