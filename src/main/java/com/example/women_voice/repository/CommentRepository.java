package com.example.women_voice.repository;

import com.example.women_voice.model.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment_tb WHERE parent_id IS NULL AND forum_post_id = :postId ORDER BY created_at DESC", nativeQuery = true)
    List<Comment> findAllByOrderByCreatedAtDesc(@Param("postId") Long postId);
}
