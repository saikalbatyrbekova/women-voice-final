package com.example.women_voice.service;

import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    List<CourseResponse> all(int page, int size);
    List<CourseResponse> findByAuthor(String email, int page, int size);
    List<CourseResponse> findByCategory(String category, int page, int size);
    List<CourseResponse> getAuthorsCourses(String token, int page, int size);
    CourseResponse getById(Long id);
    CourseResponse create(String token, CourseRequest courseRequest, MultipartFile file);
    List<CourseResponse> delete(String token, Long id);
}
