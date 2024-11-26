package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ForumTopicMapper;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import com.example.women_voice.repository.ForumTopicRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForumTopicServiceImplTest {

    @InjectMocks
    private ForumTopicServiceImpl forumTopicService;

    @Mock
    private ForumTopicRepository forumTopicRepository;

    @Mock
    private AmazonService amazonService;

    @Mock
    private ForumTopicMapper forumTopicMapper;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnForumTopicResponse_WhenValidRequest() {
        // Arrange
        String token = "valid-token";
        ForumTopicRequest request = new ForumTopicRequest();
        MultipartFile file = mock(MultipartFile.class);

        User user = new User();
        MyFile myFile = new MyFile();
        ForumTopic forumTopic = new ForumTopic();
        ForumTopicResponse response = new ForumTopicResponse();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(amazonService.uploadFile(file)).thenReturn(myFile);
        when(forumTopicMapper.toForumTopic(request, user)).thenReturn(forumTopic);
        when(forumTopicRepository.save(forumTopic)).thenReturn(forumTopic);
        when(forumTopicMapper.toResponse(forumTopic)).thenReturn(response);

        // Act
        ForumTopicResponse result = forumTopicService.create(token, request, file);

        // Assert
        assertEquals(response, result);
        verify(authService).getUserFromToken(token);
        verify(amazonService).uploadFile(file);
        verify(forumTopicMapper).toForumTopic(request, user);
        verify(forumTopicRepository).save(forumTopic);
        verify(forumTopicMapper).toResponse(forumTopic);
    }

    @Test
    void create_ShouldThrowCustomException_WhenFileUploadFails() {
        // Arrange
        String token = "valid-token";
        ForumTopicRequest request = new ForumTopicRequest();
        MultipartFile file = mock(MultipartFile.class);

        User user = new User();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(amazonService.uploadFile(file)).thenThrow(new CustomException("File upload failed", HttpStatus.BAD_REQUEST));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> forumTopicService.create(token, request, file));
        assertEquals("File upload failed", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        verify(authService).getUserFromToken(token);
        verify(amazonService).uploadFile(file);
        verifyNoInteractions(forumTopicMapper);
        verifyNoInteractions(forumTopicRepository);
    }
}
