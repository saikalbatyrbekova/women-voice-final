package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LessonMapperImplTest {

    private LessonMapperImpl lessonMapper;

    @BeforeEach
    void setUp() {
        lessonMapper = new LessonMapperImpl();
    }

    @Test
    void toLesson_ShouldMapLessonRequestToLesson() {
        // Arrange
        LessonRequest lessonRequest = new LessonRequest();
        lessonRequest.setTitle("Test Lesson");
        lessonRequest.setContent("Test Content");

        MyFile video = new MyFile();
        video.setFileName("test-video.mp4");
        video.setPath("https://example.com/videos/test-video.mp4");

        Course course = new Course();
        course.setTitle("Test Course");

        // Act
        Lesson result = lessonMapper.toLesson(lessonRequest, video, course);

        // Assert
        assertNotNull(result, "Lesson should not be null");
        assertEquals(lessonRequest.getTitle(), result.getTitle(), "Title should match the input");
        assertEquals(lessonRequest.getContent(), result.getContent(), "Content should match the input");
        assertEquals(video, result.getVideo(), "Video should match the input video");
        assertEquals(course, result.getCourse(), "Course should match the input course");
        assertNotNull(result.getCreatedAt(), "CreatedAt should be set to current time");
    }

    @Test
    void toResponse_ShouldMapLessonToLessonResponse() {
        // Arrange
        Lesson lesson = new Lesson();
        lesson.setTitle("Test Lesson");
        lesson.setContent("Test Content");
        MyFile video = new MyFile();
        video.setFileName("test-video.mp4");
        video.setPath("https://example.com/videos/test-video.mp4");
        lesson.setVideo(video);

        Course course = new Course();
        course.setTitle("Test Course");
        lesson.setCourse(course);

        User user = new User();
        user.setEmail("test@gmail.com");
        course.setCreatedBy(user);

        // Act
        LessonResponse result = lessonMapper.toResponse(lesson);

        // Assert
        assertNotNull(result, "LessonResponse should not be null");
        assertEquals(lesson.getTitle(), result.getTitle(), "Title should match the lesson's title");
        assertEquals(lesson.getContent(), result.getContent(), "Content should match the lesson's content");
        assertEquals(video.getPath(), result.getVideoUrl(), "Video URL should match the lesson's video path");
        assertEquals(lesson.getCourse().getTitle(), result.getCourse(), "Course title should match the lesson's course title");
    }

    @Test
    void toResponseList_ShouldMapListOfLessonsToListOfLessonResponses() {
        // Arrange
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Lesson 1");
        lesson1.setContent("Content 1");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Lesson 2");
        lesson2.setContent("Content 2");

        MyFile video = new MyFile();
        video.setFileName("test-video.mp4");
        video.setPath("https://example.com/videos/test-video.mp4");
        lesson1.setVideo(video);
        lesson2.setVideo(video);

        Course course = new Course();
        course.setTitle("Test Course");
        lesson1.setCourse(course);
        lesson2.setCourse(course);

        User user = new User();
        user.setEmail("test@gmail.com");
        course.setCreatedBy(user);

        List<Lesson> lessons = List.of(lesson1, lesson2);

        // Act
        List<LessonResponse> result = lessonMapper.toResponseList(lessons);

        // Assert
        assertNotNull(result, "Response list should not be null");
        assertEquals(lessons.size(), result.size(), "Size of response list should match the size of lessons list");
        assertEquals(lesson1.getTitle(), result.get(0).getTitle(), "First lesson's title should match the first response");
        assertEquals(lesson2.getTitle(), result.get(1).getTitle(), "Second lesson's title should match the second response");
    }
}
