package com.lazish.auth.scheduler;

import com.lazish.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {
    private final AuthServiceImpl authService;
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenCleanupScheduler.class);

    @Scheduled(cron = "0 0 0 */7 * *")
    public void cleanupRefreshTokens() {
        logger.info("Starting refresh token cleanup job");
        authService.cleanupInvalidTokens();
        logger.info("Finished refresh token cleanup job");
    }
}
