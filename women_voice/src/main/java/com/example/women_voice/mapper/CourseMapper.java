package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.course.CourseRequest;
import com.example.women_voice.model.dto.course.CourseResponse;

import java.util.List;

public interface CourseMapper {
    Course toCourse(User user, Course course, CourseRequest request, MyFile file);
    CourseResponse toResponse(Course course);
    List<CourseResponse> toResponseList(List<Course> courses);
}
