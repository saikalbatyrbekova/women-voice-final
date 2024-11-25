package com.example.women_voice.controller;

import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import com.example.women_voice.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/courses")
    public List<LessonResponse> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return lessonService.all(page, size);
    }


    @GetMapping("/course/{courseId}")
    public List<LessonResponse> getCourseLessons(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return lessonService.getCourseLessons(courseId, page, size);
    }

    @GetMapping("/{lessonId}")
    public LessonResponse getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }

    @PostMapping("/{courseId}")
    public LessonResponse addLesson(
            @RequestHeader("Authorization") String token,
            @PathVariable Long courseId,
            @RequestPart("request") LessonRequest lessonRequest,
            @RequestPart("file") MultipartFile video
    ) {
        return lessonService.addLesson(token, courseId, lessonRequest, video);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(
            @RequestHeader("Authorization") String token,
            @PathVariable Long lessonId
    ) {
        lessonService.deleteLesson(token, lessonId);
    }
}
