package com.example.women_voice.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "file_tb")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String fileName;
    @Column(unique = true, nullable = false)
    private String path;

    @OneToOne(mappedBy = "image")
    private Profile profile;

    @OneToOne(mappedBy = "image")
    private Course course;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
    private Lesson lesson;
}
