package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.Comment;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;

import java.util.List;

public interface CommentMapper {
    Comment toComment(CommentRequest request, User user, ForumPost post);
    Comment toReplyComment(CommentRequest request, User user, Comment parent);
    CommentResponse toResponse(Comment comment);
    List<CommentResponse> toResponseList(List<Comment> comments);
}
