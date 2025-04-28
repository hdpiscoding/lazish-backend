package com.lazish.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lazish.utils.constants.OTPConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, String> otpCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(OTPConstants.OTP_EXPIRY_MINUTES, java.util.concurrent.TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public Cache<String, Integer> attemptCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(OTPConstants.OTP_EXPIRY_MINUTES, java.util.concurrent.TimeUnit.MINUTES)
                .build();
    }
}
