package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;

import java.util.List;

public interface LessonMapper {
    Lesson toLesson(LessonRequest request, MyFile video, Course course);
    LessonResponse toResponse(Lesson lesson);
    List<LessonResponse> toResponseList(List<Lesson> lessons);
}
