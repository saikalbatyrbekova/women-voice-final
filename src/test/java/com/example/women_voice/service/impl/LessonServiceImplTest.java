package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.LessonMapper;
import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import com.example.women_voice.repository.CourseRepository;
import com.example.women_voice.repository.LessonRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceImplTest {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AuthService authService;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private AmazonService amazonService;

    @Mock
    private MultipartFile video;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCourseLessons_ShouldReturnLessonsForCourse() {
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(lessonRepository.findAllByCourse(eq(course), any(PageRequest.class))).thenReturn(List.of(new Lesson()));
        when(lessonMapper.toResponseList(anyList())).thenReturn(List.of(new LessonResponse()));

        List<LessonResponse> result = lessonService.getCourseLessons(1L, 0, 10);

        assertEquals(1, result.size());
        verify(courseRepository).findById(1L);
        verify(lessonRepository).findAllByCourse(eq(course), any(PageRequest.class));
        verify(lessonMapper).toResponseList(anyList());
    }

    @Test
    void getCourseLessons_ShouldThrowException_WhenCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> lessonService.getCourseLessons(1L, 0, 10));
        assertEquals("Course not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addLesson_ShouldAddLesson() {
        User user = new User();
        Course course = new Course();
        course.setCreatedBy(user);
        MyFile myFile = new MyFile();

        LessonRequest lessonRequest = new LessonRequest();
        Lesson lesson = new Lesson();
        LessonResponse lessonResponse = new LessonResponse();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(authService.getUserFromToken("token")).thenReturn(user);
        when(amazonService.uploadFile(video)).thenReturn(myFile);
        when(lessonMapper.toLesson(eq(lessonRequest), eq(myFile), eq(course))).thenReturn(lesson);
        when(lessonMapper.toResponse(eq(lesson))).thenReturn(lessonResponse);
        when(lessonRepository.save(eq(lesson))).thenReturn(lesson);

        LessonResponse result = lessonService.addLesson("token", 1L, lessonRequest, video);

        assertEquals(lessonResponse, result);
        verify(courseRepository).findById(1L);
        verify(authService).getUserFromToken("token");
        verify(amazonService).uploadFile(video);
        verify(lessonMapper).toLesson(eq(lessonRequest), eq(myFile), eq(course));
        verify(lessonRepository).save(eq(lesson));
    }

    @Test
    void deleteLesson_ShouldDeleteLesson() {
        User user = new User();
        Course course = new Course();
        course.setCreatedBy(user);

        MyFile myFile = new MyFile();
        myFile.setFileName("video.mp4");

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setVideo(myFile);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(authService.getUserFromToken("token")).thenReturn(user);

        lessonService.deleteLesson("token", 1L);

        verify(lessonRepository).findById(1L);
        verify(authService).getUserFromToken("token");
        verify(amazonService).deleteFile("video.mp4");
        verify(lessonRepository).delete(lesson);
    }

    @Test
    void deleteLesson_ShouldThrowException_WhenUserIsNotOwner() {
        User user = new User();
        user.setId(1L);

        User otherUser = new User();
        otherUser.setId(2L);

        Course course = new Course();
        course.setCreatedBy(otherUser);

        Lesson lesson = new Lesson();
        lesson.setCourse(course);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(authService.getUserFromToken("token")).thenReturn(user);

        CustomException exception = assertThrows(CustomException.class, () -> lessonService.deleteLesson("token", 1L));
        assertEquals("You don't have permission", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        verify(lessonRepository).findById(1L);
        verify(authService).getUserFromToken("token");
        verifyNoInteractions(amazonService);
        verifyNoMoreInteractions(lessonRepository);
    }
}
