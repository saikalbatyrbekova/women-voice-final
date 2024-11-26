package com.example.women_voice.service.impl;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private AuthService authService;

    @Mock
    private AmazonService amazonService;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void all_ShouldReturnListOfCourses() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        List<Course> courses = new ArrayList<>();
        Page<Course> coursePage = new PageImpl<>(courses, pageable, courses.size());
        courses.add(new Course());
        when(courseRepository.findAll(pageable)).thenReturn(coursePage);
        when(courseMapper.toResponseList(any())).thenReturn(List.of(new CourseResponse()));

        // Act
        List<CourseResponse> result = courseService.all(0, 5);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    void create_ShouldThrowExceptionIfTitleExists() {
        // Arrange
        String token = "token";
        CourseRequest request = new CourseRequest();
        request.setTitle("Test Title");
        User user = new User();
        when(authService.getUserFromToken(token)).thenReturn(user);
        when(courseRepository.existsByTitleAndCreatedBy(request.getTitle(), user)).thenReturn(true);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            courseService.create(token, request, mock(MultipartFile.class));
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void countAll_ShouldReturnCourseCount() {
        // Arrange
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        int result = courseService.countAll();

        // Assert
        assertEquals(1, result);
        verify(courseRepository, times(1)).findAll();
    }
}
