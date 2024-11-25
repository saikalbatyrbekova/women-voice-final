package com.example.women_voice.repository;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByCourse(Course course, PageRequest of);
}
