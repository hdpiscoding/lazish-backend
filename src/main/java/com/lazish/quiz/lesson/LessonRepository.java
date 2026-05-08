package com.lazish.quiz.lesson;

import com.lazish.quiz.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    @Query("SELECT l.topic FROM Lesson l WHERE l.id = :lessonId")
    Topic getTopicByLessonId(@Param("lessonId") UUID lessonId);
}
