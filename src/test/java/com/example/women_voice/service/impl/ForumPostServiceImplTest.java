package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ForumPostMapper;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import com.example.women_voice.repository.ForumPostRepository;
import com.example.women_voice.repository.ForumTopicRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForumPostServiceImplTest {

    @Mock
    private ForumPostRepository forumPostRepository;

    @Mock
    private ForumTopicRepository forumTopicRepository;

    @Mock
    private ForumPostMapper forumPostMapper;

    @Mock
    private AuthService authService;

    @Mock
    private AmazonService amazonService;

    @InjectMocks
    private ForumPostServiceImpl forumPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void all_ShouldReturnListOfForumPostResponses() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        List<ForumPost> posts = List.of(new ForumPost());
        when(forumPostRepository.findAll(pageable)).thenReturn(new PageImpl<>(posts));
        when(forumPostMapper.toResponseList(posts)).thenReturn(List.of(new ForumPostResponse()));

        // Act
        List<ForumPostResponse> result = forumPostService.all(0, 5);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(forumPostRepository, times(1)).findAll(pageable);
    }

    @Test
    void getTopicPosts_ShouldReturnPostsForGivenTopic() {
        // Arrange
        Long topicId = 1L;
        ForumTopic topic = new ForumTopic();
        List<ForumPost> posts = List.of(new ForumPost());
        when(forumTopicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(forumPostRepository.findAllByTopic(topic, PageRequest.of(0, 5))).thenReturn(posts);
        when(forumPostMapper.toResponseList(posts)).thenReturn(List.of(new ForumPostResponse()));

        // Act
        List<ForumPostResponse> result = forumPostService.getTopicPosts(topicId, 0, 5);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(forumTopicRepository, times(1)).findById(topicId);
        verify(forumPostRepository, times(1)).findAllByTopic(topic, PageRequest.of(0, 5));
    }

    @Test
    void getPostDetail_ShouldReturnPostDetailResponse() {
        // Arrange
        Long postId = 1L;
        ForumPost post = new ForumPost();
        ForumPostDetailResponse response = new ForumPostDetailResponse();
        when(forumPostRepository.findById(postId)).thenReturn(Optional.of(post));
        when(forumPostMapper.toDetailResponse(post)).thenReturn(response);

        // Act
        ForumPostDetailResponse result = forumPostService.getPostDetail(postId);

        // Assert
        assertNotNull(result);
        verify(forumPostRepository, times(1)).findById(postId);
    }

    @Test
    void createPost_ShouldSaveAndReturnForumPostResponse() {
        // Arrange
        String token = "token";
        Long topicId = 1L;
        ForumPostRequest request = new ForumPostRequest();
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        ForumTopic topic = new ForumTopic();
        MyFile image = new MyFile();
        ForumPost post = new ForumPost();
        ForumPostResponse response = new ForumPostResponse();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(forumTopicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(amazonService.uploadFile(file)).thenReturn(image);
        when(forumPostMapper.toForumPost(request, topic, user)).thenReturn(post);
        when(forumPostRepository.save(post)).thenReturn(post);
        when(forumPostMapper.toResponse(post)).thenReturn(response);

        // Act
        ForumPostResponse result = forumPostService.createPost(token, topicId, request, file);

        // Assert
        assertNotNull(result);
        verify(forumPostRepository, times(1)).save(post);
    }

    @Test
    void getAuthorForTopic_ShouldReturnAuthorResponse() {
        // Arrange
        Long topicId = 1L;
        ForumTopic topic = new ForumTopic();
        User author = new User();
        topic.setCreatedBy(author);
        AuthorResponse response = new AuthorResponse();

        when(forumTopicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(forumPostMapper.toAuthorResponse(author)).thenReturn(response);

        // Act
        AuthorResponse result = forumPostService.getAuthorForTopic(topicId);

        // Assert
        assertNotNull(result);
        verify(forumTopicRepository, times(1)).findById(topicId);
        verify(forumPostMapper, times(1)).toAuthorResponse(author);
    }
}
