package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.*;
import com.example.women_voice.model.dto.forumTopic.ForumTopicRequest;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumTopicMapperImplTest {

    private ForumTopicMapperImpl forumTopicMapper;

    @BeforeEach
    void setUp() {
        forumTopicMapper = new ForumTopicMapperImpl();
    }

    @Test
    void toForumTopic_ShouldMapRequestToForumTopic() {
        ForumTopicRequest request = new ForumTopicRequest();
        request.setTitle("Test Topic Title");
        request.setDescription("Test Topic Description");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        ForumTopic forumTopic = forumTopicMapper.toForumTopic(request, user);

        assertNotNull(forumTopic);
        assertEquals(request.getTitle(), forumTopic.getTitle());
        assertEquals(request.getDescription(), forumTopic.getDescription());
        assertEquals(user, forumTopic.getCreatedBy());
        assertNotNull(forumTopic.getCreatedAt());
    }

    @Test
    void toResponse_ShouldMapForumTopicToResponse() {
        MyFile image = new MyFile();
        image.setPath("path/to/image");

        User user = new User();
        user.setEmail("author@example.com");

        ForumPost post1 = new ForumPost();
        ForumPost post2 = new ForumPost();

        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setId(1L);
        forumTopic.setTitle("Test Topic Title");
        forumTopic.setDescription("Test Topic Description");
        forumTopic.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        forumTopic.setCreatedBy(user);
        forumTopic.setImage(image);
        forumTopic.setPosts(List.of(post1, post2));

        ForumTopicResponse response = forumTopicMapper.toResponse(forumTopic);

        assertNotNull(response);
        assertEquals(forumTopic.getId(), response.getId());
        assertEquals(user.getEmail(), response.getAuthor());
        assertEquals(forumTopic.getTitle(), response.getTitle());
        assertEquals(forumTopic.getDescription(), response.getDescription());
        assertEquals("Nov 01, 2024", response.getCreatedAt());
        assertEquals(image.getPath(), response.getImagePath());
        assertEquals(2, response.getAmountPosts());
    }

    @Test
    void toResponse_ShouldHandleNullPosts() {
        MyFile image = new MyFile();
        image.setPath("path/to/image");

        User user = new User();
        user.setEmail("author@example.com");

        ForumTopic forumTopic = new ForumTopic();
        forumTopic.setId(1L);
        forumTopic.setTitle("Test Topic Title");
        forumTopic.setDescription("Test Topic Description");
        forumTopic.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        forumTopic.setCreatedBy(user);
        forumTopic.setImage(image);
        forumTopic.setPosts(null); // No posts

        ForumTopicResponse response = forumTopicMapper.toResponse(forumTopic);

        assertNotNull(response);
        assertEquals(forumTopic.getId(), response.getId());
        assertEquals(user.getEmail(), response.getAuthor());
        assertEquals(forumTopic.getTitle(), response.getTitle());
        assertEquals(forumTopic.getDescription(), response.getDescription());
        assertEquals("Nov 01, 2024", response.getCreatedAt());
        assertEquals(image.getPath(), response.getImagePath());
        assertEquals(0, response.getAmountPosts()); // Amount of posts should be 0
    }

    @Test
    void toResponseList_ShouldMapListOfForumTopicsToResponseList() {
        User user = new User();
        user.setEmail("author@example.com");

        MyFile image = new MyFile();
        image.setPath("path/to/image");

        ForumTopic topic1 = new ForumTopic();
        topic1.setId(1L);
        topic1.setTitle("Topic 1");
        topic1.setDescription("Description 1");
        topic1.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        topic1.setCreatedBy(user);
        topic1.setImage(image);
        topic1.setPosts(List.of(new ForumPost(), new ForumPost()));

        ForumTopic topic2 = new ForumTopic();
        topic2.setId(2L);
        topic2.setTitle("Topic 2");
        topic2.setDescription("Description 2");
        topic2.setCreatedAt(LocalDateTime.of(2024, 11, 2, 12, 45));
        topic2.setCreatedBy(user);
        topic2.setImage(image);
        topic2.setPosts(null); // No posts for topic 2

        List<ForumTopicResponse> responses = forumTopicMapper.toResponseList(List.of(topic1, topic2));

        assertNotNull(responses);
        assertEquals(2, responses.size());

        ForumTopicResponse response1 = responses.get(0);
        assertEquals(topic1.getId(), response1.getId());
        assertEquals(topic1.getTitle(), response1.getTitle());
        assertEquals(topic1.getDescription(), response1.getDescription());
        assertEquals("Nov 01, 2024", response1.getCreatedAt());
        assertEquals(image.getPath(), response1.getImagePath());
        assertEquals(2, response1.getAmountPosts());

        ForumTopicResponse response2 = responses.get(1);
        assertEquals(topic2.getId(), response2.getId());
        assertEquals(topic2.getTitle(), response2.getTitle());
        assertEquals(topic2.getDescription(), response2.getDescription());
        assertEquals("Nov 02, 2024", response2.getCreatedAt());
        assertEquals(image.getPath(), response2.getImagePath());
        assertEquals(0, response2.getAmountPosts()); // No posts
    }
}
