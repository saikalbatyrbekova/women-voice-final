package com.example.women_voice.controller;

import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import com.example.women_voice.service.ForumPostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/forum_post")
public class ForumPostController {
    private final ForumPostService forumPostService;

    @GetMapping("/all/{topicId}")
    public List<ForumPostResponse> getForumPosts(
            @PathVariable(required = false) Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (topicId != null) {
            return forumPostService.getTopicPosts(topicId, page, size);
        } else {
            return forumPostService.all(page, size);
        }
    }

    @GetMapping("/{id}")
    public ForumPostResponse getPost(@PathVariable Long id) {
        return forumPostService.getPost(id);
    }

    @PostMapping("/{forumTopicId}")
    public ForumPostResponse createPost(
            @RequestHeader("Authorization") String token,
            @PathVariable Long forumTopicId,
            @RequestBody ForumPostRequest request
    ) {
        return forumPostService.createPost(token, forumTopicId, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        forumPostService.delete(token, id);
    }
}
