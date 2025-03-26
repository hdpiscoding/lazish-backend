package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.entity.User;
import com.lazish.service.implementations.JwtServiceImpl;
import com.lazish.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController extends BaseController {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtServiceImpl;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getAllUsers() {
        return buildResponse(userService.getAllUsers(), HttpStatus.OK, "Get all users successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me")
    public ResponseEntity<Object> getUserById(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtServiceImpl.extractUserId(authHeader.substring(7));
        return buildResponse(userService.getUserById(userId), HttpStatus.OK, "Get user successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me/diamonds")
    public ResponseEntity<Object> getUserDiamonds(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtServiceImpl.extractUserId(authHeader.substring(7));
        return buildResponse(userService.getUserDiamonds(userId), HttpStatus.OK, "Get user diamonds successfully");
    }
}
