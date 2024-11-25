package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;

import java.util.List;

public interface ForumPostMapper {
    ForumPost toForumPost(ForumPostRequest request, ForumTopic topic, User user);
    ForumPostResponse toResponse(ForumPost post);
    ForumPostDetailResponse toDetailResponse(ForumPost post);
    List<ForumPostResponse> toResponseList(List<ForumPost> posts);
    AuthorResponse toAuthorResponse(User user);
}
