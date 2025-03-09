package com.lazish.repository;

import com.lazish.entity.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, UUID> {
}
