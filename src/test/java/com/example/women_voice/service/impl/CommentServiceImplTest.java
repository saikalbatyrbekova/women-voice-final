package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.CommentMapper;
import com.example.women_voice.model.domain.Comment;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;
import com.example.women_voice.repository.CommentRepository;
import com.example.women_voice.repository.ForumPostRepository;
import com.example.women_voice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private AuthService authService;

    @Mock
    private ForumPostRepository forumPostRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        // Mock input data
        String token = "test-token";
        Long postId = 1L;
        CommentRequest commentRequest = new CommentRequest();
        User user = new User();
        ForumPost post = new ForumPost();
        Comment comment = new Comment();
        CommentResponse expectedResponse = new CommentResponse();

        // Mock dependencies
        when(authService.getUserFromToken(token)).thenReturn(user);
        when(forumPostRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentMapper.toComment(commentRequest, user, post)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toResponse(comment)).thenReturn(expectedResponse);

        // Call the method
        CommentResponse actualResponse = commentService.addComment(commentRequest, token, postId);

        // Verify results
        assertEquals(expectedResponse, actualResponse);
        verify(authService).getUserFromToken(token);
        verify(forumPostRepository).findById(postId);
        verify(commentMapper).toComment(commentRequest, user, post);
        verify(commentRepository).save(comment);
        verify(commentMapper).toResponse(comment);
    }

    @Test
    void testAddReply() {
        // Mock input data
        String token = "test-token";
        Long parentId = 1L;
        CommentRequest request = new CommentRequest();
        User user = new User();
        Comment parentComment = new Comment();
        Comment replyComment = new Comment();
        Long postId = 2L;
        List<CommentResponse> expectedResponseList = List.of(new CommentResponse());

        // Mock parent comment
        ForumPost forumPost = new ForumPost();
        forumPost.setId(postId);
        parentComment.setForumPost(forumPost);

        // Mock dependencies
        when(authService.getUserFromToken(token)).thenReturn(user);
        when(commentRepository.findById(parentId)).thenReturn(Optional.of(parentComment));
        when(commentMapper.toReplyComment(request, user, parentComment)).thenReturn(replyComment);
        when(commentRepository.save(replyComment)).thenReturn(replyComment);
        when(commentMapper.toResponseList(anyList())).thenReturn(expectedResponseList);

        // Call the method
        List<CommentResponse> actualResponseList = commentService.addReply(request, token, parentId);

        // Verify results
        assertEquals(expectedResponseList, actualResponseList);
        verify(authService).getUserFromToken(token);
        verify(commentRepository).findById(parentId);
        verify(commentMapper).toReplyComment(request, user, parentComment);
        verify(commentRepository).save(replyComment);
        verify(commentMapper).toResponseList(anyList());
    }

    @Test
    void testAll() {
        // Mock input data
        Long postId = 1L;
        List<Comment> comments = List.of(new Comment());
        List<CommentResponse> expectedResponseList = List.of(new CommentResponse());

        // Mock dependencies
        when(commentRepository.findAllByOrderByCreatedAtDesc(postId)).thenReturn(comments);
        when(commentMapper.toResponseList(comments)).thenReturn(expectedResponseList);

        // Call the method
        List<CommentResponse> actualResponseList = commentService.all(postId);

        // Verify results
        assertEquals(expectedResponseList, actualResponseList);
        verify(commentRepository).findAllByOrderByCreatedAtDesc(postId);
        verify(commentMapper).toResponseList(comments);
    }

    @Test
    void testAddComment_ForumPostNotFound() {
        // Mock input data
        String token = "test-token";
        Long postId = 1L;
        CommentRequest commentRequest = new CommentRequest();

        // Mock dependencies
        when(authService.getUserFromToken(token)).thenReturn(new User());
        when(forumPostRepository.findById(postId)).thenReturn(Optional.empty());

        // Assert exception
        CustomException exception = assertThrows(CustomException.class, () ->
                commentService.addComment(commentRequest, token, postId));

        assertEquals("Forum Post not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(authService).getUserFromToken(token);
        verify(forumPostRepository).findById(postId);
    }
}
