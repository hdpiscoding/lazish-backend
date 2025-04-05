package com.lazish.service.interfaces;

import com.lazish.dto.LessonDTO;
import com.lazish.entity.Lesson;
import com.lazish.entity.Topic;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    Lesson createLesson(LessonDTO lessonDTO, Topic topic);
    LessonDTO updateLesson(UUID id, LessonDTO lessonDTO);
    LessonDTO addLessonToTopic(LessonDTO lessonDTO, UUID topicId);
    void deleteLesson(UUID id);
    LessonDTO getLesson(UUID id);
}
