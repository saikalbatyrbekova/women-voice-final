package com.example.women_voice.controller;

import com.example.women_voice.model.dto.auth.LoginRequest;
import com.example.women_voice.model.dto.auth.RegisterRequest;
import com.example.women_voice.model.dto.comment.CommentResponse;
import com.example.women_voice.model.dto.course.CourseResponse;
import com.example.women_voice.model.dto.forumPost.AuthorResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostDetailResponse;
import com.example.women_voice.model.dto.forumPost.ForumPostResponse;
import com.example.women_voice.model.dto.forumTopic.ForumTopicResponse;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/pages")
public class PageController {
    private final LessonService lessonService;
    private final CourseService courseService;
    private final ProfileService profileService;
    private final ForumTopicService forumTopicService;
    private final ForumPostService forumPostService;
    private final CommentService commentService;

    @GetMapping("/index")
    public String index(
            Model model
    ) {
        List<LessonResponse> lessons = lessonService.all(0, 6);
        List<CourseResponse> courses = courseService.all(0, 6);
        List<ExpertResponse> experts = profileService.getExperts(0, 6);
        List<ForumPostResponse> forumPosts = forumPostService.all(0, 6);

        model.addAttribute("forumPosts", forumPosts);
        model.addAttribute("experts", experts);
        model.addAttribute("courses", courses);
        model.addAttribute("lessons", lessons);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/post")
    public String blog(
            @RequestParam(required = false) Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<ForumPostResponse> forumPosts;
        AuthorResponse author = null;

        if (topicId != null) {
            forumPosts = forumPostService.getTopicPosts(topicId, page, size);
            author = forumPostService.getAuthorForTopic(topicId);
        } else {
            forumPosts = forumPostService.all(page, size);
        }

        model.addAttribute("forumPosts", forumPosts);
        model.addAttribute("author", author);
        return "forum_post";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/course")
    public String course(
            @RequestParam(defaultValue = "0") int pageLesson,
            @RequestParam(defaultValue = "10") int sizeLesson,
            @RequestParam(defaultValue = "0") int pageCourse,
            @RequestParam(defaultValue = "10") int sizeCourse,
            Model model
    ) {
        List<LessonResponse> lessons = lessonService.all(pageLesson, sizeLesson);
        int totalLessons = lessonService.countAll();
        int totalPagesLesson = (int) Math.ceil((double) totalLessons / sizeLesson);

        model.addAttribute("pageLesson", pageLesson);
        model.addAttribute("totalPagesLesson", totalPagesLesson);

        List<CourseResponse> courses = courseService.all(pageCourse, sizeCourse);
        int totalCourse = courseService.countAll();
        int totalPagesCourse = (int) Math.ceil((double) totalCourse / sizeCourse);

        model.addAttribute("page", pageCourse);
        model.addAttribute("totalPages", totalPagesCourse);

        model.addAttribute("courses", courses);
        model.addAttribute("lessons", lessons);
        return "course";
    }

    @GetMapping("/post_detail/{id}")
    public String single(
            @PathVariable Long id,
            Model model
    ) {
        ForumPostDetailResponse detailResponse = forumPostService.getPostDetail(id);
        List<CommentResponse> comments = commentService.all(id);

        model.addAttribute("detail", detailResponse);
        model.addAttribute("comments", comments);
        model.addAttribute("quantity", comments.size());
        return "post_detail";
    }

    @GetMapping("/forum_topic")
    public String forumTopic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<ForumTopicResponse> forumTopics = forumTopicService.all(page, size);
        model.addAttribute("forumTopics", forumTopics);
        return "forum_topic";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/experts")
    public String experts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<ExpertResponse> experts = profileService.getExperts(page, size);
        model.addAttribute("experts", experts);
        return "teacher";
    }
}
