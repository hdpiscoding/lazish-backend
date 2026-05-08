package com.lazish.notification;

import jakarta.mail.MessagingException;

public interface EmailService {
    String generateOTP();
    void sendOTPEmail(String to, String otp) throws MessagingException;
}
