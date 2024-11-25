package com.example.women_voice.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "username cannot be empty")
    @Size(min = 2, max = 24, message = "Length username from 2 to 24")
    private String username;
    @Email(message = "Incorrect email")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 12, message = "Length password from 4 to 12")
    private String password;
    @NotBlank(message = "Role cannot be empty")
    private String role;
}
