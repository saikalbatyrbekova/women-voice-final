package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.LessonMapper;
import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import com.example.women_voice.repository.CourseRepository;
import com.example.women_voice.repository.LessonRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AuthService authService;
    private final LessonMapper lessonMapper;
    private final AmazonService amazonService;

    @Override
    public List<LessonResponse> all(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return lessonMapper.toResponseList(lessonRepository.findAll(pageable).stream().toList());
    }

    @Override
    public List<LessonResponse> getCourseLessons(Long courseId, int page, int size) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CustomException("Course not found", HttpStatus.NOT_FOUND));
        return lessonMapper.toResponseList(lessonRepository.findAllByCourse(course, PageRequest.of(page, size)));
    }

    @Override
    public LessonResponse getLesson(Long lessonId) {
        return lessonMapper.toResponse(lessonRepository.findById(lessonId).orElseThrow(() -> new CustomException("Lesson not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public LessonResponse addLesson(String token, Long courseId, LessonRequest lessonRequest, MultipartFile video) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CustomException("Course not found", HttpStatus.NOT_FOUND));
        if (!course.getCreatedBy().equals(authService.getUserFromToken(token))) {
            throw new CustomException("You don't have permission", HttpStatus.FORBIDDEN);
        }
        return lessonMapper.toResponse(lessonRepository.save(lessonMapper.toLesson(lessonRequest, amazonService.uploadFile(video), course)));
    }

    @Override
    public void deleteLesson(String token, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new CustomException("Lesson not found", HttpStatus.NOT_FOUND));
        if (!authService.getUserFromToken(token).equals(lesson.getCourse().getCreatedBy())) {
            throw new CustomException("You don't have permission", HttpStatus.FORBIDDEN);
        }
        amazonService.deleteFile(lesson.getVideo().getFileName());
        lessonRepository.delete(lesson);
    }

    @Override
    public int countAll() {
        return lessonRepository.findAll().size();
    }
}
