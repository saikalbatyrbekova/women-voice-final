package com.example.women_voice.controller;

import com.example.women_voice.model.dto.auth.AuthResponse;
import com.example.women_voice.model.dto.auth.LoginRequest;
import com.example.women_voice.model.dto.auth.RegisterRequest;
import com.example.women_voice.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.register(request);
        cookieOperation(authResponse, response);
        return "redirect:/pages/index";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);
        cookieOperation(authResponse, response);
        return "redirect:/pages/index";
    }

    private void cookieOperation(AuthResponse authResponse, HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("access_token", authResponse.getAccess_token());
        Cookie idCookie = new Cookie("user_id", authResponse.getId().toString());

        tokenCookie.setMaxAge(24 * 60 * 60);
        tokenCookie.setPath("/");
        idCookie.setMaxAge(24 * 60 * 60);
        idCookie.setPath("/");

        response.addCookie(tokenCookie);
        response.addCookie(idCookie);
    }
}
