package com.lazish.service.implementations;

import com.lazish.dto.AuthResponseDTO;
import com.lazish.dto.LoginDTO;
import com.lazish.dto.RegisterDTO;
import com.lazish.dto.UserDTO;
import com.lazish.entity.Token;
import com.lazish.entity.User;
import com.lazish.mapper.UserMapper;
import com.lazish.repository.TokenRepository;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.AuthService;
import com.lazish.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponseDTO register(RegisterDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullname(request.getFullname());
        user.setAge(request.getAge());
        user.setRole(Role.ADMIN);
        User newUser = userRepository.save(user);
        String token = jwtServiceImpl.generateToken(newUser);
        tokenRepository.save(Token
                .builder()
                .user(newUser)
                .token(token)
                .expired(false)
                .revoked(false)
                .build());
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
        String token = jwtServiceImpl.generateToken(user);
        tokenRepository.save(Token
                .builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .build());
        UserDTO userDTO = userMapper.toDto(user);
        return AuthResponseDTO
                .builder()
                .user(userDTO)
                .token(token)
                .build();
    }
}
