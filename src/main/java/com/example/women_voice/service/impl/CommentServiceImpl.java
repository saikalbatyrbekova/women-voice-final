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
import com.example.women_voice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final ForumPostRepository forumPostRepository;

    @Override
    public CommentResponse addComment(CommentRequest commentRequest, String token, Long postId) {
        User user = authService.getUserFromToken(token);
        ForumPost post = forumPostRepository.findById(postId).orElseThrow(() -> new CustomException("Forum Post not found", HttpStatus.NOT_FOUND));
        return commentMapper.toResponse(commentRepository.save(commentMapper.toComment(commentRequest, user, post)));
    }

    @Override
    public List<CommentResponse> addReply(CommentRequest request, String token, Long parentId) {
        User user = authService.getUserFromToken(token);
        Comment parent = commentRepository.findById(parentId).orElseThrow(() -> new CustomException("Comment not found", HttpStatus.NOT_FOUND));

        commentRepository.save(commentMapper.toReplyComment(request, user, parent));
        return all(parent.getForumPost().getId());
    }

    @Override
    public List<CommentResponse> all(Long postId) {
        return commentMapper.toResponseList(commentRepository.findAllByOrderByCreatedAtDesc(postId));
    }
}
