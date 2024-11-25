package com.example.women_voice.controller;

import com.example.women_voice.model.dto.comment.CommentRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;
import com.example.women_voice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public CommentResponse addComment(
            @RequestBody CommentRequest commentRequest,
            @RequestHeader("Authorization") String token,
            @PathVariable Long postId
    ) {
        return commentService.addComment(commentRequest, token, postId);
    }

    @PostMapping("/reply/{parentId}")
    public List<CommentResponse> addReply(
            @RequestBody CommentRequest request,
            @RequestHeader("Authorization") String token,
            @PathVariable Long parentId
    ) {
        return commentService.addReply(request, token, parentId);
    }

    @GetMapping("/{postId}")
    public List<CommentResponse> all(@PathVariable Long postId) {
        return commentService.all(postId);
    }
}
