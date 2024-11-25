package com.example.women_voice.model.dto.comment;

import lombok.Data;

import java.util.List;

@Data
public class CommentResponse {
    private Long id;
    private String userImageUrl;
    private String userName;
    private String createdAt;
    private String comment;
    private List<CommentResponse> replies;
}
