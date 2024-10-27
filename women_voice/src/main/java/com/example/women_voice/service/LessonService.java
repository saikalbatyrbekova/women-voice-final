package com.example.women_voice.service;

import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonService {
    List<LessonResponse> all(int page, int size);
    List<LessonResponse> getCourseLessons(Long courseId, int page, int size);
    LessonResponse getLesson(Long lessonId);
    LessonResponse addLesson(String token, Long courseId, LessonRequest lessonRequest, MultipartFile video);
    void deleteLesson(String token, Long lessonId);
}
