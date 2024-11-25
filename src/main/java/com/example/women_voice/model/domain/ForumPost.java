package com.example.women_voice.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "forum_post")
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private ForumTopic topic;

    @OneToOne(cascade = CascadeType.REMOVE)
    private MyFile image;

    @OneToMany(mappedBy = "forumPost")
    private List<Comment> comments;
}
