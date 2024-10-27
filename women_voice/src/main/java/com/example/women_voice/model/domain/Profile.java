package com.example.women_voice.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_profile")
public class Profile {

    @Id
    private Long id;

    private String fullName;
    private String bio;
    private String location;
    private String educationLevel;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_url", referencedColumnName = "path")
    private MyFile image;
}
