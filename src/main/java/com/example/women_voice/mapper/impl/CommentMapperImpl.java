package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.CommentMapper;
import com.example.women_voice.model.domain.Comment;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public Comment toComment(CommentRequest request, User user, ForumPost post) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setForumPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    @Override
    public Comment toReplyComment(CommentRequest request, User user, Comment parent) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setParent(parent);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setForumPost(parent.getForumPost());
        return comment;
    }

    @Override
    public CommentResponse toResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setUserImageUrl(comment.getUser().getProfile().getImage().getPath());
        response.setUserName(comment.getUser().getProfile().getFullName());
        response.setCreatedAt(getFormatedDate(comment.getCreatedAt()));
        response.setComment(comment.getContent());
        response.setReplies(toResponseList(comment.getReplies()));
        return response;
    }

    @Override
    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return new ArrayList<>();
        }
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : comments) {
            responses.add(toResponse(comment));
        }
        return responses;
    }

    private String getFormatedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' hh:mma", Locale.ENGLISH);
        return date != null ? date.format(formatter).toLowerCase() : "";
    }
}
