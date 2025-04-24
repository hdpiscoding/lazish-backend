package com.lazish.service.implementations;

import com.lazish.service.interfaces.OTPService;
import com.lazish.utils.constants.RedisConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OTPServiceImpl implements OTPService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveOTP(String email, String otp) {
        redisTemplate.opsForValue().set(email, otp, RedisConstants.OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
        String attempKey = "otp:attempt:" + email;
        redisTemplate.opsForValue().increment(attempKey);
        redisTemplate.expire(attempKey, 1, TimeUnit.HOURS);
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        if (storedOtp == null) {
            return false;
        }
        boolean isValid = storedOtp.equals(otp);
        if (isValid) {
            redisTemplate.delete(email);
        }
        return isValid;
    }

    @Override
    public boolean canSendOTP(String email) {
        String attemptKey = "otp:attempt:" + email;
        String attempts = redisTemplate.opsForValue().get(attemptKey);
        return attempts == null || Integer.parseInt(attempts) <= RedisConstants.MAX_ATTEMPTS;
    }
}
