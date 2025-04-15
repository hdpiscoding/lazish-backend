package com.lazish.service.implementations;

import com.lazish.dto.UserDTO;
import com.lazish.entity.User;
import com.lazish.mapper.UserMapper;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO getUserById(UUID id) {
        User u = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        u.setReels(null);
        return userMapper.toDto(u);
    }

    @Override
    @Transactional
    public UserDTO updateUserInfo(UUID id, UserDTO user) {
        logger.info("Updating user with id: {}", id);
        User temp_user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if(user.getFullname() != null) {
            temp_user.setFullname(user.getFullname());
        }
        if(user.getPhone() != null) {
            temp_user.setPhone(user.getPhone());
        }
        if(user.getAge() != 0) {
            temp_user.setAge(user.getAge());
        }
        if(user.getDob() != null) {
            temp_user.setDob(user.getDob());
        }
        if(user.getAvatar() != null) {
            temp_user.setAvatar(user.getAvatar());
        }

        User u = userRepository.save(temp_user);
        u.setReels(null);

        return userMapper.toDto(u);
    }

    @Override
    public void changePassword(UUID id, String newPassword) {
        logger.info("Changing password for user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        logger.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll()
                .stream()
                .peek(user -> user.setReels(null))
                .toList();
        return userMapper.toDtoList(users);
    }

    @Override
    public int getUserStreak(UUID userId) {
        return 0;
    }

    @Override
    public long getUserDiamonds(UUID userId) {
        return userRepository.getUserDiamonds(userId);
    }
}
