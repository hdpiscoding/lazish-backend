package com.lazish.service.interfaces;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    String generateOTP();
    void sendOTPEmail(String to, String otp) throws MessagingException;
}
