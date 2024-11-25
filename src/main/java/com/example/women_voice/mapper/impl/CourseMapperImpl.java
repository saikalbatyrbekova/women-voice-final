package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.CourseMapper;
import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapperImpl implements CourseMapper {
    @Override
    public Course toCourse(User user, Course course, CourseRequest request, MyFile file) {
        course.setTitle(request.getTitle());
        course.setImage(file);
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setCreatedAt(LocalDateTime.now());
        course.setCreatedBy(user);
        return course;
    }

    @Override
    public CourseResponse toResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCategory(course.getCategory());
        response.setCreatedBy(course.getCreatedBy().getUsername());
        response.setCreatedAt(course.getCreatedAt());
        response.setImagePath(course.getImage().getPath());
        if (course.getLessons() != null) {
            response.setAmountLessons(course.getLessons().size());
        } else {
            response.setAmountLessons(0);
        }
        return response;
    }

    @Override
    public List<CourseResponse> toResponseList(List<Course> courses) {
        List<CourseResponse> responseList = new ArrayList<>();
        for (Course course : courses) {
            responseList.add(toResponse(course));
        }
        return responseList;
    }
}
