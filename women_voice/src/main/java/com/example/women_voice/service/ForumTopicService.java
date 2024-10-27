package com.example.women_voice.service;

import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;

import java.util.List;

public interface ForumTopicService {
    List<ForumTopicResponse> all(int page, int size);
    List<ForumTopicResponse> getAuthorsForums(String token, int page, int size);
    ForumTopicResponse getForumTopic(Long topicId);
    ForumTopicResponse create(String token, ForumTopicRequest request);
    void delete(String token, Long topicId);
}
