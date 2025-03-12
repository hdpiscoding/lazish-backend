package com.lazish.service.interfaces;

import com.lazish.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    User getUserById(UUID id);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
    List<User> getAllUsers();
    int getUserStreak(UUID userId);
}
