package com.example.women_voice.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Incorrect email")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 12, message = "Length password from 4 to 12")
    private String password;
}
