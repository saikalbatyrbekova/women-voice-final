package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.ForumPostMapper;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ForumPostMapperImpl implements ForumPostMapper {
    @Override
    public ForumPost toForumPost(ForumPostRequest request, ForumTopic topic, User user) {
        ForumPost post = new ForumPost();
        post.setTitle(request.getTitle());
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
        response.setTitle(post.getTitle());
        response.setCreatedAt(getFormatedDate(post.getCreatedAt()));
        response.setImageUrl(post.getImage().getPath());
        return response;
    }

    @Override
    public ForumPostDetailResponse toDetailResponse(ForumPost post) {
        ForumPostDetailResponse response = new ForumPostDetailResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCreatedAt(getFormatedDate(post.getCreatedAt()));
        response.setImageUrl(post.getImage().getPath());
        response.setAuthor(toAuthorResponse(post.getUser()));
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

    @Override
    public AuthorResponse toAuthorResponse(User user) {
        AuthorResponse response = new AuthorResponse();
        response.setId(user.getId());
        response.setFullName(user.getProfile().getFullName());
        response.setBio(user.getProfile().getBio());
        response.setImagePath(user.getProfile().getImage().getPath());
        return response;
    }

    private String getFormatedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
        return date != null ? date.format(formatter) : "";
    }
}
