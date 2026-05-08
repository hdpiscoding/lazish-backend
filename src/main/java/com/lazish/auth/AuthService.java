package com.lazish.auth;

import com.lazish.auth.dto.AuthResponseDTO;
import com.lazish.auth.dto.LoginDTO;
import com.lazish.auth.dto.RegisterDTO;
import com.lazish.user.UserDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterDTO request);
    AuthResponseDTO login(LoginDTO request);
    AuthResponseDTO refresh(String refreshToken);
    void logout(String refreshToken);
    void forgotPassword(String email);
    UserDTO resetPassword(String otp, String email, String newPassword);
}
