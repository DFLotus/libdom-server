package com.server.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dto.UserJson;
import com.server.model.Users;
import com.server.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserJson userJson) {
        if (userJson.getUsername() != null && userJson.getUsername().isEmpty()
                || userJson.getPassword() != null && userJson.getPassword().isEmpty()
                || userJson.getEmail() != null && userJson.getEmail().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Empty response");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Users newUser = new Users();
        newUser.setUsername(userJson.getUsername());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encrpyedPassword = bcrypt.encode(userJson.getPassword());
        newUser.setPassword(encrpyedPassword);
        newUser.setEmail(userJson.getEmail());
        newUser.setProfilePictureUrl(userJson.getProfilePictureUrl());

        try {
            Users registedUser = this.userService.registerUser(newUser);
            return ResponseEntity.status(201).body(registedUser);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch. " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserJson userJson) {
        if (userJson.getUsername() != null && userJson.getUsername().isEmpty()
                || userJson.getPassword() != null && userJson.getPassword().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Empty response");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            Users foundUser = this.userService.userLogin(userJson.getUsername(), userJson.getPassword());
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("username", foundUser.getUsername());
            userInfo.put("email", foundUser.getEmail());
            userInfo.putIfAbsent("profile_picture", foundUser.getProfilePictureUrl());
            userInfo.put("user_id", String.valueOf(foundUser.getId()));

            return ResponseEntity.status(201).body(userInfo);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to login with error: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}