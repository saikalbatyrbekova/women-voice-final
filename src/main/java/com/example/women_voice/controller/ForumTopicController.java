package com.example.women_voice.controller;

import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import com.example.women_voice.service.ForumTopicService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/forum_topics")
public class ForumTopicController {
    private final ForumTopicService forumTopicService;

    @GetMapping
    public List<ForumTopicResponse> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return forumTopicService.all(page, size);
    }

    @GetMapping("/my")
    public List<ForumTopicResponse> getAuthorsForums(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return forumTopicService.getAuthorsForums(token, page, size);
    }

    @GetMapping("/{topicId}")
    public ForumTopicResponse getForumTopic(@PathVariable Long topicId) {
        return forumTopicService.getForumTopic(topicId);
    }

    @PostMapping
    public ForumTopicResponse create(
            @RequestHeader("Authorization") String token,
            @RequestPart(name = "request") ForumTopicRequest request,
            @RequestPart(name = "file") MultipartFile file
    ) {
        return forumTopicService.create(token, request, file);
    }

    @DeleteMapping("/{topicId}")
    public void delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long topicId
    ) {
        forumTopicService.delete(token, topicId);
    }
}
