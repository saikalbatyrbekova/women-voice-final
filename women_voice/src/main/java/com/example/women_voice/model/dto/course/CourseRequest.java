package com.example.women_voice.model.dto.course;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "The length of the title should be from 2 to 100")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    @Lob
    private String description;
    @NotBlank(message = "Category cannot be empty")
    private String category;
}
