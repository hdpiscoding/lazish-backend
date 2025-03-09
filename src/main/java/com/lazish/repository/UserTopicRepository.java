package com.lazish.repository;

import com.lazish.entity.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTopicRepository extends JpaRepository<UserTopic, UUID> {
}
