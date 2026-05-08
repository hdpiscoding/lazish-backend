package com.lazish.auth;

import com.lazish.common.base.BaseController;
import com.lazish.auth.dto.AuthResponseDTO;
import com.lazish.auth.dto.LoginDTO;
import com.lazish.auth.dto.RefreshTokenRequestDTO;
import com.lazish.auth.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO request) {
        AuthResponseDTO response = authService.register(request);
        return buildResponse(response, HttpStatus.CREATED, "User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO request) {
        AuthResponseDTO response = authService.login(request);
        return buildResponse(response, HttpStatus.OK, "User logged in successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshTokenRequestDTO request) {
        AuthResponseDTO response = authService.refresh(request.getRefreshToken());
        return buildResponse(response, HttpStatus.OK, "Token refreshed successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody RefreshTokenRequestDTO request) {
        authService.logout(request.getRefreshToken());
        return buildResponse(null, HttpStatus.OK, "Logged out successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody String email) {
        authService.forgotPassword(email);
        return buildResponse(null, HttpStatus.OK, "Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody String otp, @RequestBody String email, @RequestBody String newPassword) {
        return buildResponse(authService.resetPassword(otp, email, newPassword), HttpStatus.OK, "Password reset successfully");
    }
}
