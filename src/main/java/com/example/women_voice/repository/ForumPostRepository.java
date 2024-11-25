package com.example.women_voice.repository;

import com.example.women_voice.model.domain.ForumPost;
import com.example.women_voice.model.domain.ForumTopic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findAllByTopic(ForumTopic topic, PageRequest of);
}
