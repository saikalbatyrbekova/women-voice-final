package com.example.women_voice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.CourseMapper;
import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;
import com.example.women_voice.repository.CourseRepository;
import com.example.women_voice.repository.UserRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final AuthService authService;
    private final AmazonService amazonService;
    private final AmazonS3 amazonS3;

    @Override
    public List<CourseResponse> all(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseMapper.toResponseList(courseRepository.findAll(pageable).stream().toList());
    }

    @Override
    public List<CourseResponse> findByAuthor(String email, int page, int size) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return courseMapper.toResponseList(courseRepository.findAllByCreatedBy(user, PageRequest.of(page, size)));
    }

    @Override
    public List<CourseResponse> findByCategory(String category, int page, int size) {
        return courseMapper.toResponseList(courseRepository.findAllByCategory(category, PageRequest.of(page, size)));
    }

    @Override
    public List<CourseResponse> getAuthorsCourses(String token, int page, int size) {
        return courseMapper.toResponseList(courseRepository.findAllByCreatedBy(authService.getUserFromToken(token), PageRequest.of(page, size)));
    }

    @Override
    public CourseResponse getById(Long id) {
        return courseMapper.toResponse(courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public CourseResponse create(String token, CourseRequest courseRequest, MultipartFile file) {
        User user = authService.getUserFromToken(token);
        if (courseRepository.existsByTitleAndCreatedBy(courseRequest.getTitle(), user)) {
            throw new CustomException("Title already exists", HttpStatus.CONFLICT);
        }
        return courseMapper.toResponse(courseRepository.save(courseMapper.toCourse(user, new Course(), courseRequest, amazonService.uploadFile(file))));
    }

    @Override
    public List<CourseResponse> delete(String token, Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not found", HttpStatus.NOT_FOUND));
        User user = authService.getUserFromToken(token);
        if (!course.getCreatedBy().equals(user)) {
            throw new CustomException("You don't have permission", HttpStatus.FORBIDDEN);
        }
        amazonService.deleteFile(course.getImage().getFileName());
        courseRepository.delete(course);
        return getAuthorsCourses(token, 0, 10); // todo: think about pagination
    }

    @Override
    public int countAll() {
        return courseRepository.findAll().size();
    }
}
