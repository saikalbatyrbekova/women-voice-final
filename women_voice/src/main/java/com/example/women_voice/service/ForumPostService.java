package com.example.women_voice.service;

import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;

import java.util.List;

public interface ForumPostService {
    List<ForumPostResponse> all(int page, int size);
    List<ForumPostResponse> getTopicPosts(Long topicId, int page, int size);
    ForumPostResponse getPost(Long id);
    ForumPostResponse createPost(String token, Long forumTopicId, ForumPostRequest request);
    void delete(String token, Long id);
}
