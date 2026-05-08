package com.lazish.auth;

import com.lazish.auth.dto.AuthResponseDTO;
import com.lazish.auth.dto.LoginDTO;
import com.lazish.auth.dto.RegisterDTO;
import com.lazish.auth.exception.InvalidRefreshToken;
import com.lazish.auth.exception.RefreshTokenIsExpired;
import com.lazish.auth.exception.RefreshTokenIsRevoked;
import com.lazish.notification.EmailServiceImpl;
import com.lazish.user.UserDTO;
import com.lazish.user.User;
import com.lazish.exception.TooManyRequestsException;
import com.lazish.user.UserMapper;
import com.lazish.notification.EmailService;
import com.lazish.user.UserRepository;
import com.lazish.security.JwtService;
import com.lazish.common.utils.enums.Role;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OTPService otpService;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${REFRESH_TOKEN_TTL_DAYS}")
    private int refreshTokenTtlDays;

    @Value("${REFRESH_TOKEN_PEPPER}")
    private String refreshTokenPepper;


    @Override
    public AuthResponseDTO register(RegisterDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullname(request.getFullname());
        user.setAge(request.getAge());
        user.setRole(Role.USER);
        User newUser = userRepository.save(user);
        String token = jwtService.generateToken(newUser);
        String refreshToken = issueRefreshToken(newUser);
        return AuthResponseDTO
                .builder()
                .user(userMapper.toDto(newUser))
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.generateToken(user);
        String refreshToken = issueRefreshToken(user);
        UserDTO userDTO = userMapper.toDto(user);
        return AuthResponseDTO
                .builder()
                .user(userDTO)
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponseDTO refresh(String refreshToken) {
        RefreshToken storedToken = findValidRefreshToken(refreshToken);
        revokeToken(storedToken);
        String newAccessToken = jwtService.generateToken(storedToken.getUser());
        String newRefreshToken = issueRefreshToken(storedToken.getUser());
        return AuthResponseDTO.builder()
                .user(userMapper.toDto(storedToken.getUser()))
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        RefreshToken storedToken = findValidRefreshToken(refreshToken);
        revokeToken(storedToken);
    }

    @Override
    public void forgotPassword(String email) {
        try {
            if (!userRepository.existsByEmail(email)) {
                return;
            }
            if (!otpService.canSendOTP(email)){
                throw new TooManyRequestsException("Too many requests. Please try again later.");
            }
            String otp = emailService.generateOTP();
            otpService.saveOTP(email, otp);
            emailService.sendOTPEmail(email, otp);
        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDTO resetPassword(String otp, String email, String newPassword) {
        boolean isValid = otpService.verifyOTP(email, otp);
        if (!isValid) {
            throw new RuntimeException("Invalid OTP");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    private String issueRefreshToken(User user) {
        String rawToken = generateRefreshTokenValue();
        String tokenHash = hashRefreshToken(rawToken);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .expiresAt(LocalDateTime.now().plusDays(refreshTokenTtlDays))
                .isRevoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    private RefreshToken findValidRefreshToken(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            throw new RuntimeException("Refresh token is required");
        }
        String tokenHash = hashRefreshToken(rawToken);
        RefreshToken storedToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new InvalidRefreshToken("Invalid refresh token"));
        if (storedToken.isRevoked()) {
            throw new RefreshTokenIsRevoked("Refresh token revoked");
        }
        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            revokeToken(storedToken);
            throw new RefreshTokenIsExpired("Refresh token expired");
        }
        return storedToken;
    }

    private void revokeToken(RefreshToken token) {
        token.setRevoked(true);
        token.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(token);
    }

    private String generateRefreshTokenValue() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashRefreshToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String tokenWithPepper = rawToken + refreshTokenPepper;
            byte[] hashed = digest.digest(tokenWithPepper.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash refresh token", e);
        }
    }

    public void cleanupInvalidTokens() {
        int deletedCount = refreshTokenRepository.deleteInvalidTokens(LocalDateTime.now());
        logger.info("Deleted {} refresh tokens", deletedCount);
    }
}
