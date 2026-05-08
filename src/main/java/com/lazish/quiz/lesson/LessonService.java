package com.lazish.quiz.lesson;

import com.lazish.quiz.topic.Topic;

import java.util.UUID;

public interface LessonService {
    Lesson createLesson(LessonDTO lessonDTO, Topic topic);
    LessonDTO updateLesson(UUID id, LessonDTO lessonDTO);
    LessonDTO addLessonToTopic(LessonDTO lessonDTO, UUID topicId);
    void deleteLesson(UUID id);
    LessonDTO getLesson(UUID id);
    void finishUserLesson(UUID userId, UUID lessonId);
    int getUserProgress(UUID userId, UUID lessonId);
}
