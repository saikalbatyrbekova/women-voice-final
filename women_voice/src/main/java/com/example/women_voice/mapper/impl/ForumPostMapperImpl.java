package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.ForumPostMapper;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForumPostMapperImpl implements ForumPostMapper {
    @Override
    public ForumPost toForumPost(ForumPostRequest request, ForumTopic topic, User user) {
        ForumPost post = new ForumPost();
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setTopic(topic);
        post.setUser(user);
        return post;
    }

    @Override
    public ForumPostResponse toResponse(ForumPost post) {
        ForumPostResponse response = new ForumPostResponse();
        response.setId(post.getId());
        response.setAuthor(post.getUser().getEmail());
        response.setTopic(post.getTopic().getTitle());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        return response;
    }

    @Override
    public List<ForumPostResponse> toResponseList(List<ForumPost> posts) {
        List<ForumPostResponse> responses = new ArrayList<>();
        for (ForumPost post : posts) {
            responses.add(toResponse(post));
        }
        return responses;
    }
}
