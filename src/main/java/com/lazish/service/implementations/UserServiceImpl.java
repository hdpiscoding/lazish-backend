package com.lazish.service.implementations;

import com.lazish.entity.User;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        logger.info("Creating user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public User updateUser(UUID id, User user) {
        logger.info("Updating user with id: {}", id);
        User temp_user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        temp_user.setFullname(user.getFullname());
        temp_user.setEmail(user.getEmail());
        temp_user.setPhone(user.getPhone());
        temp_user.setAge(user.getAge());
        temp_user.setDob(user.getDob());

        return userRepository.save(temp_user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        logger.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));

        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public int getUserStreak(UUID userId) {
        return 0;
    }
}
