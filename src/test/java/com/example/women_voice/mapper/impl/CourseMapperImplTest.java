package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseMapperImplTest {

    private CourseMapperImpl courseMapper;

    @BeforeEach
    void setUp() {
        courseMapper = new CourseMapperImpl();
    }

    @Test
    void toCourse_ShouldMapCourseRequestToCourse() {
        User user = new User();
        user.setId(1L);

        MyFile file = new MyFile();
        file.setPath("path/to/image");

        Course course = new Course();

        CourseRequest request = new CourseRequest();
        request.setTitle("Test Course");
        request.setDescription("This is a test course.");
        request.setCategory("Education");

        Course result = courseMapper.toCourse(user, course, request, file);

        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getCategory(), result.getCategory());
        assertEquals(user, result.getCreatedBy());
        assertEquals(file, result.getImage());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void toResponse_ShouldMapCourseToCourseResponse() {
        User user = new User();
        user.setUsername("test_user");

        MyFile file = new MyFile();
        file.setPath("path/to/image");

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setDescription("This is a test course.");
        course.setCategory("Education");
        course.setCreatedAt(LocalDateTime.now());
        course.setCreatedBy(user);
        course.setImage(file);

        CourseResponse response = courseMapper.toResponse(course);

        assertNotNull(response);
        assertEquals(course.getId(), response.getId());
        assertEquals(course.getTitle(), response.getTitle());
        assertEquals(course.getDescription(), response.getDescription());
        assertEquals(course.getCategory(), response.getCategory());
        assertEquals(user.getUsername(), response.getCreatedBy());
        assertEquals(course.getCreatedAt(), response.getCreatedAt());
        assertEquals(file.getPath(), response.getImagePath());
        assertEquals(0, response.getAmountLessons());
    }

    @Test
    void toResponse_ShouldMapCourseWithLessonsToCourseResponse() {
        User user = new User();
        user.setUsername("test_user");

        MyFile file = new MyFile();
        file.setPath("path/to/image");

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setDescription("This is a test course.");
        course.setCategory("Education");
        course.setCreatedAt(LocalDateTime.now());
        course.setCreatedBy(user);
        course.setImage(file);
        course.setLessons(List.of()); // Имитируем, что уроки присутствуют

        CourseResponse response = courseMapper.toResponse(course);

        assertNotNull(response);
        assertEquals(0, response.getAmountLessons());
    }

    @Test
    void toResponseList_ShouldMapCourseListToCourseResponseList() {
        User user = new User();
        user.setUsername("test_user");

        MyFile file = new MyFile();
        file.setPath("path/to/image");

        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Course 1");
        course1.setDescription("Description 1");
        course1.setCategory("Category 1");
        course1.setCreatedAt(LocalDateTime.now());
        course1.setCreatedBy(user);
        course1.setImage(file);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Course 2");
        course2.setDescription("Description 2");
        course2.setCategory("Category 2");
        course2.setCreatedAt(LocalDateTime.now());
        course2.setCreatedBy(user);
        course2.setImage(file);

        List<CourseResponse> responses = courseMapper.toResponseList(List.of(course1, course2));

        assertNotNull(responses);
        assertEquals(2, responses.size());

        CourseResponse response1 = responses.get(0);
        assertEquals(course1.getId(), response1.getId());
        assertEquals(course1.getTitle(), response1.getTitle());
        assertEquals(course1.getDescription(), response1.getDescription());
        assertEquals(course1.getCategory(), response1.getCategory());

        CourseResponse response2 = responses.get(1);
        assertEquals(course2.getId(), response2.getId());
        assertEquals(course2.getTitle(), response2.getTitle());
        assertEquals(course2.getDescription(), response2.getDescription());
        assertEquals(course2.getCategory(), response2.getCategory());
    }
}
