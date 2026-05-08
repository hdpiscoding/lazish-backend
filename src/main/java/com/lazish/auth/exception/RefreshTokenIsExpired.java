package com.lazish.auth.exception;

public class RefreshTokenIsExpired extends RuntimeException {
    public RefreshTokenIsExpired(String message) {
        super(message);
    }
}
