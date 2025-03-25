package com.lazish.service.interfaces;

import com.lazish.dto.UserDTO;
import com.lazish.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    User updateUserInfo(UUID id, UserDTO user);
    void deleteUser(UUID id);
    List<User> getAllUsers();
    int getUserStreak(UUID userId);
    long getUserDiamonds(UUID userId);
}
