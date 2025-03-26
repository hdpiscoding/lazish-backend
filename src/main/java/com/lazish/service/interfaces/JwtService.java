package com.lazish.service.interfaces;

import com.lazish.entity.User;
import io.jsonwebtoken.Claims;

import java.util.UUID;
import java.util.function.Function;

public interface JwtService {
    String generateToken(User user);
    String extractEmail(String token);
    UUID extractUserId(String token);
    String extractRole(String token);
    boolean isTokenExpired(String token);
}
