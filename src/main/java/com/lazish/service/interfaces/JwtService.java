package com.lazish.service.interfaces;

import com.lazish.entity.User;

public interface JwtService {
    String generateToken(User user);
    String extractEmail(String token);
}
