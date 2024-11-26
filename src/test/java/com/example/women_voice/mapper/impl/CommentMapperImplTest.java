package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.*;
import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperImplTest {

    private CommentMapperImpl commentMapper;

    @BeforeEach
    void setUp() {
        commentMapper = new CommentMapperImpl();
    }

    @Test
    void toComment_ShouldMapRequestToComment() {
        // Arrange
        CommentRequest request = new CommentRequest();
        request.setContent("Test comment");

        User user = new User();
        user.setId(1L);

        ForumPost post = new ForumPost();
        post.setId(1L);

        // Act
        Comment result = commentMapper.toComment(request, user, post);

        // Assert
        assertNotNull(result);
        assertEquals(request.getContent(), result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(post, result.getForumPost());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void toReplyComment_ShouldMapRequestToReplyComment() {
        // Arrange
        CommentRequest request = new CommentRequest();
        request.setContent("Reply comment");

        User user = new User();
        user.setId(1L);

        Comment parentComment = new Comment();
        parentComment.setId(1L);
        ForumPost post = new ForumPost();
        parentComment.setForumPost(post);

        // Act
        Comment result = commentMapper.toReplyComment(request, user, parentComment);

        // Assert
        assertNotNull(result);
        assertEquals(request.getContent(), result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(parentComment, result.getParent());
        assertEquals(post, result.getForumPost());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void toResponse_ShouldMapCommentToResponse() {
        // Arrange
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test comment");
        comment.setCreatedAt(LocalDateTime.of(2024, 11, 26, 14, 30));

        User user = new User();
        user.setId(1L);

        Profile profile = new Profile(); // Mocked profile object
        profile.setFullName("John Doe");
        MyFile file = new MyFile();
        file.setPath("https://example.com/image.jpg");
        profile.setImage(file);
        user.setProfile(profile);

        comment.setUser(user);

        // Act
        CommentResponse response = commentMapper.toResponse(comment);

        // Assert
        assertNotNull(response);
        assertEquals(comment.getId(), response.getId());
    }

}
