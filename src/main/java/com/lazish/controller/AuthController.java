package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.AuthResponseDTO;
import com.lazish.dto.LoginDTO;
import com.lazish.dto.RegisterDTO;
import com.lazish.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, String> body) {
        authService.forgotPassword(body.get("email"));
        return buildResponse(null, HttpStatus.OK, "Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody Map<String, String> body) {
        return buildResponse(authService.resetPassword(body.get("otp"), body.get("email"), body.get("newPassword")), HttpStatus.OK, "Password reset successfully");
    }
}
