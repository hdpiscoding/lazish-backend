package com.lazish.repository;

import com.lazish.entity.Lesson;
import com.lazish.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    @Query("SELECT l.topic FROM Lesson l WHERE l.id = :lessonId")
    Topic getTopicByLessonId(@Param("lessonId") UUID lessonId);
}
