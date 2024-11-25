package com.example.women_voice.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "course_tb")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "email")
    private User createdBy;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_url", referencedColumnName = "path")
    private MyFile image;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;
}
