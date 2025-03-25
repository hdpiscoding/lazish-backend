package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.entity.User;
import com.lazish.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return buildResponse(userService.getAllUsers(), HttpStatus.OK, "Get all users successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        return buildResponse(userService.getUserById(id), HttpStatus.OK, "Get user successfully");
    }

    @GetMapping("/{id}/diamonds")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getUserDiamonds(@PathVariable UUID id) {
        return buildResponse(userService.getUserDiamonds(id), HttpStatus.OK, "Get user diamonds successfully");
    }
}
