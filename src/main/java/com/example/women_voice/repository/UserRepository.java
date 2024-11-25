package com.example.women_voice.repository;

import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.enums.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role, PageRequest of);
}
