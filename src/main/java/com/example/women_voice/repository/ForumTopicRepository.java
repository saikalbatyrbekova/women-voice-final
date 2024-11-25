package com.example.women_voice.repository;

import com.example.women_voice.model.domain.ForumTopic;
import com.example.women_voice.model.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumTopicRepository extends JpaRepository<ForumTopic, Long> {
    List<ForumTopic> findAllByCreatedBy(User user, PageRequest of);
}