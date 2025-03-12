package com.lazish.repository;

import com.lazish.entity.UserStreak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStreakRepository extends JpaRepository<UserStreak, UUID> {
    @Query(value = "SELECT streak FROM UserStreak WHERE user = :userId", nativeQuery = true)
    int getUserStreak(@Param("userId") UUID userId);
}
