package com.example.women_voice.service;

import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ForumPostService {
    List<ForumPostResponse> all(int page, int size);
    List<ForumPostResponse> getTopicPosts(Long topicId, int page, int size);
    ForumPostDetailResponse getPostDetail(Long id);
    ForumPostResponse createPost(String token, Long forumTopicId, ForumPostRequest request, MultipartFile file);
    void delete(String token, Long id);
    AuthorResponse getAuthorForTopic(Long topicId);
}
