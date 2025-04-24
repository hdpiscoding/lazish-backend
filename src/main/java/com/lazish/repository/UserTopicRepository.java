package com.lazish.repository;

import com.lazish.entity.UserTopic;
import com.lazish.utils.key.UserTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, UserTopicId> {
    @Query("SELECT u.lesson_completed FROM UserTopic u WHERE u.user.id = :userId AND u.topic.id = :topicId")
    int getUserProgressByTopic(@Param("userId") UUID userId, @Param("topicId") UUID topicId);
}
