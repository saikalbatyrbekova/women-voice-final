package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ForumPostMapper;
import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import com.example.women_voice.repository.ForumPostRepository;
import com.example.women_voice.repository.ForumTopicRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import com.example.women_voice.service.ForumPostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ForumPostServiceImpl implements ForumPostService {
    private final ForumPostRepository forumPostRepository;
    private final ForumTopicRepository forumTopicRepository;
    private final ForumPostMapper forumPostMapper;
    private final AuthService authService;
    private final AmazonService amazonService;

    @Override
    public List<ForumPostResponse> all(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return forumPostMapper.toResponseList(forumPostRepository.findAll(pageable).getContent());
    }

    @Override
    public List<ForumPostResponse> getTopicPosts(Long topicId, int page, int size) {
        ForumTopic topic = forumTopicRepository.findById(topicId).orElseThrow(() -> new CustomException("Forum Topic not found", HttpStatus.NOT_FOUND));
        return forumPostMapper.toResponseList(forumPostRepository.findAllByTopic(topic, PageRequest.of(page, size)));
    }

    @Override
    public ForumPostDetailResponse getPostDetail(Long id) {
        return forumPostMapper.toDetailResponse(forumPostRepository.findById(id).orElseThrow(() -> new CustomException("Forum Post not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public ForumPostResponse createPost(String token, Long forumTopicId, ForumPostRequest request, MultipartFile file) {
        User user = authService.getUserFromToken(token);
        ForumTopic topic = forumTopicRepository.findById(forumTopicId).orElseThrow(() -> new CustomException("Forum Topic not found", HttpStatus.NOT_FOUND));
        MyFile image = amazonService.uploadFile(file);
        ForumPost forumPost = forumPostMapper.toForumPost(request, topic, user);
        forumPost.setImage(image);
        return forumPostMapper.toResponse(forumPostRepository.save(forumPost));
    }

    @Override
    public void delete(String token, Long id) {
        User user = authService.getUserFromToken(token);
        ForumPost post = forumPostRepository.findById(id).orElseThrow(() -> new CustomException("Forum Post not found", HttpStatus.NOT_FOUND));
        if (!post.getTopic().getCreatedBy().equals(user)) {
            throw new CustomException("You don't have permission", HttpStatus.FORBIDDEN);
        }
        amazonService.deleteFile(post.getImage().getFileName());
        forumPostRepository.delete(post);
    }

    @Override
    public AuthorResponse getAuthorForTopic(Long topicId) {
        ForumTopic topic = forumTopicRepository.findById(topicId).orElseThrow(() -> new CustomException("Forum Topic not found", HttpStatus.NOT_FOUND));
        return forumPostMapper.toAuthorResponse(topic.getCreatedBy());
    }
}
