package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ForumTopicMapper;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import com.example.women_voice.repository.ForumTopicRepository;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.ForumTopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ForumTopicServiceImpl implements ForumTopicService {
    private final ForumTopicRepository forumTopicRepository;
    private final ForumTopicMapper forumTopicMapper;
    private final AuthService authService;

    @Override
    public List<ForumTopicResponse> all(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return forumTopicMapper.toResponseList(forumTopicRepository.findAll(pageable).stream().toList());
    }

    @Override
    public List<ForumTopicResponse> getAuthorsForums(String token, int page, int size) {
        User user = authService.getUserFromToken(token);
        return forumTopicMapper.toResponseList(forumTopicRepository.findAllByCreatedBy(user, PageRequest.of(page, size)));
    }

    @Override
    public ForumTopicResponse getForumTopic(Long topicId) {
        return forumTopicMapper.toResponse(forumTopicRepository.findById(topicId).orElseThrow(() -> new CustomException("Forum not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public ForumTopicResponse create(String token, ForumTopicRequest request) {
        User user = authService.getUserFromToken(token);
        return forumTopicMapper.toResponse(forumTopicRepository.save(forumTopicMapper.toForumTopic(request, user)));
    }

    @Override
    public void delete(String token, Long topicId) {
        User user = authService.getUserFromToken(token);
        ForumTopic forumTopic = forumTopicRepository.findById(topicId).orElseThrow(() -> new CustomException("Forum not found", HttpStatus.NOT_FOUND));
        if (!forumTopic.getCreatedBy().equals(user)) {
            throw new CustomException("You don't have permission", HttpStatus.FORBIDDEN);
        }
        forumTopicRepository.delete(forumTopic);
    }
}
