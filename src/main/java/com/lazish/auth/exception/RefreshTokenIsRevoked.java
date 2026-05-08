package com.lazish.auth.exception;

public class RefreshTokenIsRevoked extends RuntimeException {
    public RefreshTokenIsRevoked(String message) {
        super(message);
    }
}
