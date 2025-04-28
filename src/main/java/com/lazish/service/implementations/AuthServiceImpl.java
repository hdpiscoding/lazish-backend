package com.lazish.service.implementations;

import com.lazish.dto.AuthResponseDTO;
import com.lazish.dto.LoginDTO;
import com.lazish.dto.RegisterDTO;
import com.lazish.dto.UserDTO;
import com.lazish.entity.User;
import com.lazish.exception.InvalidOTPException;
import com.lazish.exception.TooManyRequestsException;
import com.lazish.mapper.UserMapper;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.AuthService;
import com.lazish.service.interfaces.EmailService;
import com.lazish.service.interfaces.JwtService;
import com.lazish.service.interfaces.OTPService;
import com.lazish.utils.enums.Role;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


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
        return AuthResponseDTO
                .builder()
                .user(userMapper.toDto(newUser))
                .token(token)
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
        UserDTO userDTO = userMapper.toDto(user);
        return AuthResponseDTO
                .builder()
                .user(userDTO)
                .token(token)
                .build();
    }

    @Override
    public void forgotPassword(String email) {
        try {
            if (!userRepository.existsByEmail(email)) {
                throw new RuntimeException("User not found");
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
            throw new InvalidOTPException("OTP is invalid or expired. Please try again.");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
