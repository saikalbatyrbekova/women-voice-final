package com.example.women_voice.repository;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByCreatedBy(User user, PageRequest of);

    List<Course> findAllByCategory(String category, PageRequest of);
    boolean existsByTitleAndCreatedBy(String title, User user);
}
