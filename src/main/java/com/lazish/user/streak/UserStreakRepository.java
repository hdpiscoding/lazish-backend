package com.lazish.user.streak;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import com.lazish.user.User;

@Repository
public interface UserStreakRepository extends JpaRepository<UserStreak, UUID> {
    @Query(value = "SELECT current_streak FROM user_streak WHERE user = :userId", nativeQuery = true)
    int getUserStreak(@Param("userId") UUID userId);
    @Query(value = "SELECT longest_streak FROM user_streak WHERE user = :userId", nativeQuery = true)
    int getUserLongestStreak(@Param("userId") UUID userId);
    Optional<UserStreak> findByUser(User user);
}
