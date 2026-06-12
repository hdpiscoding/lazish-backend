package com.lazish.user.streak;

import com.lazish.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserStreakServiceImpl implements UserStreakService {

    private final UserStreakRepository userStreakRepository;

    @Override
    @Transactional
    public void updateLoginStreak(User user) {
        if (user.getRole() != com.lazish.common.utils.enums.Role.USER) {
            return;
        }
        LocalDate today = LocalDate.now();
        Optional<UserStreak> optionalStreak = userStreakRepository.findByUser(user);
        UserStreak streak;
        if (optionalStreak.isPresent()) {
            streak = optionalStreak.get();
            LocalDate lastActivity = streak.getLastActivityDate();
            if (lastActivity == null || lastActivity.isBefore(today.minusDays(1))) {
                streak.setCurrentStreak(1);
            } else if (lastActivity.isEqual(today.minusDays(1))) {
                // Increment streak
                streak.setCurrentStreak(streak.getCurrentStreak() + 1);
            } else if (lastActivity.isEqual(today)) {
                // Already logged in today
                return;
            }

            if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                streak.setLongestStreak(streak.getCurrentStreak());
            }
            streak.setLastActivityDate(today);
        } else {
            // First time
            streak = UserStreak.builder()
                    .user(user)
                    .currentStreak(1)
                    .longestStreak(1)
                    .lastActivityDate(today)
                    .build();
        }

        userStreakRepository.save(streak);
    }

    @Override
    public int getUserStreak(UUID userId) {
        return userStreakRepository.getUserStreak(userId);
    }

    @Override
    public int getUserLongestStreak(UUID userId) {
        return userStreakRepository.getUserLongestStreak(userId);
    }
}

