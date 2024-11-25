package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.LessonMapper;
import com.example.women_voice.model.domain.Course;
import com.example.women_voice.model.domain.Lesson;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.dto.lesson.LessonRequest;
import com.example.women_voice.model.dto.lesson.LessonResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapperImpl implements LessonMapper {
    @Override
    public Lesson toLesson(LessonRequest request, MyFile vide, Course course) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideo(vide);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setCourse(course);
        return lesson;
    }

    @Override
    public LessonResponse toResponse(Lesson lesson) {
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setAuthor(lesson.getCourse().getCreatedBy().getEmail());
        lessonResponse.setCourse(lesson.getCourse().getTitle());
        lessonResponse.setTitle(lesson.getTitle());
        lessonResponse.setContent(lesson.getContent());
        lessonResponse.setVideoUrl(lesson.getVideo().getPath());
        lessonResponse.setCreatedAt(lesson.getCreatedAt());
        return lessonResponse;
    }

    @Override
    public List<LessonResponse> toResponseList(List<Lesson> lessons) {
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonResponseList.add(toResponse(lesson));
        }
        return lessonResponseList;
    }
}
