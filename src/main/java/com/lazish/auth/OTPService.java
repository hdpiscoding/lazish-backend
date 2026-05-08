package com.lazish.auth;

public interface OTPService {
    void saveOTP(String email, String otp);
    boolean verifyOTP(String email, String otp);
    boolean canSendOTP(String email);
}
