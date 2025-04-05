package com.lazish.service.interfaces;

import com.lazish.dto.UserDTO;
import com.lazish.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    UserDTO updateUserInfo(UUID id, UserDTO user);
    void changePassword(UUID id, String newPassword);
    void deleteUser(UUID id);
    List<UserDTO> getAllUsers();
    int getUserStreak(UUID userId);
    long getUserDiamonds(UUID userId);
}
