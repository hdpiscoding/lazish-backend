package com.lazish.security;

import com.lazish.user.User;

import java.util.UUID;

public interface JwtService {
    String generateToken(User user);
    String extractEmail(String token);
    UUID extractUserId(String token);
    String extractRole(String token);
    boolean isTokenExpired(String token);
}
