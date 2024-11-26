package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.*;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostRequest;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumPostMapperImplTest {

    private ForumPostMapperImpl forumPostMapper;

    @BeforeEach
    void setUp() {
        forumPostMapper = new ForumPostMapperImpl();
    }

    @Test
    void toForumPost_ShouldMapRequestToForumPost() {
        ForumPostRequest request = new ForumPostRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");

        ForumTopic topic = new ForumTopic();
        topic.setId(1L);

        User user = new User();
        user.setId(1L);

        ForumPost post = forumPostMapper.toForumPost(request, topic, user);

        assertNotNull(post);
        assertEquals(request.getTitle(), post.getTitle());
        assertEquals(request.getContent(), post.getContent());
        assertEquals(topic, post.getTopic());
        assertEquals(user, post.getUser());
        assertNotNull(post.getCreatedAt());
    }

    @Test
    void toResponse_ShouldMapForumPostToResponse() {
        MyFile image = new MyFile();
        image.setPath("path/to/image");

        ForumPost post = new ForumPost();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        post.setImage(image);

        ForumPostResponse response = forumPostMapper.toResponse(post);

        assertNotNull(response);
        assertEquals(post.getId(), response.getId());
        assertEquals(post.getTitle(), response.getTitle());
        assertEquals("Nov 01, 2024", response.getCreatedAt());
        assertEquals(image.getPath(), response.getImageUrl());
    }

    @Test
    void toDetailResponse_ShouldMapForumPostToDetailResponse() {
        User user = new User();
        user.setId(1L);
        Profile profile = new Profile();
        profile.setFullName("John Doe");
        profile.setBio("Bio example");
        MyFile profileImage = new MyFile();
        profileImage.setPath("path/to/profile/image");
        profile.setImage(profileImage);
        user.setProfile(profile);

        MyFile postImage = new MyFile();
        postImage.setPath("path/to/post/image");

        ForumPost post = new ForumPost();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        post.setImage(postImage);
        post.setUser(user);

        ForumPostDetailResponse response = forumPostMapper.toDetailResponse(post);

        assertNotNull(response);
        assertEquals(post.getId(), response.getId());
        assertEquals(post.getTitle(), response.getTitle());
        assertEquals(post.getContent(), response.getContent());
        assertEquals("Nov 01, 2024", response.getCreatedAt());
        assertEquals(postImage.getPath(), response.getImageUrl());

        AuthorResponse authorResponse = response.getAuthor();
        assertNotNull(authorResponse);
        assertEquals(user.getId(), authorResponse.getId());
        assertEquals(profile.getFullName(), authorResponse.getFullName());
        assertEquals(profile.getBio(), authorResponse.getBio());
        assertEquals(profileImage.getPath(), authorResponse.getImagePath());
    }

    @Test
    void toResponseList_ShouldMapListOfForumPostsToResponseList() {
        MyFile image = new MyFile();
        image.setPath("path/to/image");

        ForumPost post1 = new ForumPost();
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setCreatedAt(LocalDateTime.of(2024, 11, 1, 10, 30));
        post1.setImage(image);

        ForumPost post2 = new ForumPost();
        post2.setId(2L);
        post2.setTitle("Title 2");
        post2.setCreatedAt(LocalDateTime.of(2024, 11, 2, 12, 45));
        post2.setImage(image);

        List<ForumPostResponse> responses = forumPostMapper.toResponseList(List.of(post1, post2));

        assertNotNull(responses);
        assertEquals(2, responses.size());

        ForumPostResponse response1 = responses.get(0);
        assertEquals(post1.getId(), response1.getId());
        assertEquals(post1.getTitle(), response1.getTitle());
        assertEquals("Nov 01, 2024", response1.getCreatedAt());
        assertEquals(image.getPath(), response1.getImageUrl());

        ForumPostResponse response2 = responses.get(1);
        assertEquals(post2.getId(), response2.getId());
        assertEquals(post2.getTitle(), response2.getTitle());
        assertEquals("Nov 02, 2024", response2.getCreatedAt());
        assertEquals(image.getPath(), response2.getImageUrl());
    }

    @Test
    void toAuthorResponse_ShouldMapUserToAuthorResponse() {
        User user = new User();
        user.setId(1L);

        Profile profile = new Profile();
        profile.setFullName("John Doe");
        profile.setBio("Bio example");

        MyFile image = new MyFile();
        image.setPath("path/to/profile/image");
        profile.setImage(image);

        user.setProfile(profile);

        AuthorResponse response = forumPostMapper.toAuthorResponse(user);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(profile.getFullName(), response.getFullName());
        assertEquals(profile.getBio(), response.getBio());
        assertEquals(image.getPath(), response.getImagePath());
    }
}
