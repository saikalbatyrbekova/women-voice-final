package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.ForumTopicMapper;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ForumTopicMapperImpl implements ForumTopicMapper {
    @Override
    public ForumTopic toForumTopic(ForumTopicRequest request, User user) {
        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setTitle(request.getTitle());
        forumTopic.setDescription(request.getDescription());
        forumTopic.setCreatedAt(LocalDateTime.now());
        forumTopic.setCreatedBy(user);
        return forumTopic;
    }

    @Override
    public ForumTopicResponse toResponse(ForumTopic forumTopic) {
        ForumTopicResponse response = new ForumTopicResponse();
        response.setId(forumTopic.getId());
        response.setAuthor(forumTopic.getCreatedBy().getEmail());
        response.setTitle(forumTopic.getTitle());
        response.setDescription(forumTopic.getDescription());
        response.setCreatedAt(getFormatedDate(forumTopic.getCreatedAt()));
        response.setImagePath(forumTopic.getImage().getPath());
        if (forumTopic.getPosts() != null) {
            response.setAmountPosts(forumTopic.getPosts().size());
        } else {
            response.setAmountPosts(0);
        }
        return response;
    }

    @Override
    public List<ForumTopicResponse> toResponseList(List<ForumTopic> forumTopics) {
        List<ForumTopicResponse> responseList = new ArrayList<>();
        for (ForumTopic forumTopic : forumTopics) {
            responseList.add(toResponse(forumTopic));
        }
        return responseList;
    }

    private String getFormatedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
        return date != null ? date.format(formatter) : "";
    }
}
