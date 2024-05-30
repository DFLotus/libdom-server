package com.server.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dto.UserJson;
import com.server.model.Users;
import com.server.repository.UsersRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UsersController {
    private UsersRepository userRepository;

    public UsersController(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody UserJson userJson) {
        Users newUser = new Users();
        newUser.setUsername(userJson.getUsername());
        newUser.setPassword(userJson.getPassword());
        newUser.setEmail(userJson.getEmail());
        newUser.setProfilePictureUrl(userJson.getProfilePictureUrl());

        // this.userRepository.save(newUser);

        return ResponseEntity.status(201).body(this.userRepository.save(newUser));
    }

}
