package com.lazish.repository;

import com.lazish.entity.UserLesson;
import com.lazish.utils.key.UserLessonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, UserLessonId> {
    @Query("SELECT ul.progress FROM UserLesson ul WHERE ul.user.id = :userId AND ul.lesson.id = :lessonId")
    Integer getUserProgress(@Param("userId") UUID userId, @Param("lessonId") UUID lessonId);
}
