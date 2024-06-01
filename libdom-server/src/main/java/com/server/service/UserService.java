package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.server.model.Users;
import com.server.repository.UsersRepository;

@Service
public class UserService {
    @Autowired
    private UsersRepository userRepository;

    public Users registerUser(Users user) throws Exception {
        try {
            Users existingEmail = this.userRepository.findByEmail(user.getEmail());
            Users existingUsername = this.userRepository.findByUsername(user.getUsername());

            if (existingEmail != null || existingUsername != null) {
                throw new Exception("User already exists");
            }

            return this.userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new Exception("Email already exists", e);
        }
    }

    public Users userLogin(String username, String password) throws Exception {
        try {
            Users user = this.userRepository.findByUsername(username);

            if (user == null) {
                throw new Exception("User not found");
            }

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

            if (!bcrypt.matches(password, user.getPassword())) {
                throw new Exception("Incorrect password");
            }

            return user;

        } catch (Error e) {
            throw new Exception("Failed to id user with error: ", e);
        }
    }

}
