package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;

import java.util.List;

public interface ForumTopicMapper {
    ForumTopic toForumTopic(ForumTopicRequest request, User user);
    ForumTopicResponse toResponse(ForumTopic forumTopic);
    List<ForumTopicResponse> toResponseList(List<ForumTopic> forumTopics);
}
