package com.example.women_voice.service;

import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(CommentRequest commentRequest, String token, Long postId);
    List<CommentResponse> addReply(CommentRequest request, String token, Long parentId);
    List<CommentResponse> all(Long postId);
}
