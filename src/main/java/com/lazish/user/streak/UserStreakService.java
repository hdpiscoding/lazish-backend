package com.lazish.user.streak;

import com.lazish.user.User;

import java.util.UUID;

public interface UserStreakService {
    void updateLoginStreak(User user);
    int getUserStreak(UUID userId);
    int getUserLongestStreak(UUID userId);
}

