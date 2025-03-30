package com.lazish.service.interfaces;

import com.lazish.dto.AuthResponseDTO;
import com.lazish.dto.LoginDTO;
import com.lazish.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthResponseDTO register(RegisterDTO request);
    AuthResponseDTO login(LoginDTO request);
    void forgotPassword(String newPassword);
}
