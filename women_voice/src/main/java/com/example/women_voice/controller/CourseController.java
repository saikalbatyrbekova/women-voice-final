package com.example.women_voice.controller;

import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;
import com.example.women_voice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<CourseResponse> allCourses(
            @RequestParam(required = false) String authorEmail,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (authorEmail != null && category == null) {
            return courseService.findByAuthor(authorEmail, page, size);
        } else if (category != null) {
            return courseService.findByCategory(category, page, size);
        } else {
            return courseService.all(page, size);
        }
    }

    @GetMapping("/my")
    List<CourseResponse> getAuthorsCourses(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return courseService.getAuthorsCourses(token, page, size);
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PostMapping("/create")
    public CourseResponse create(
            @RequestHeader("Authorization") String token,
            @RequestPart(name = "request") CourseRequest request,
            @RequestPart(name = "file") MultipartFile file
    ) {
        return courseService.create(token, request, file);
    }

    @DeleteMapping("/{id}")
    List<CourseResponse> delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        return courseService.delete(token, id);
    }
}
