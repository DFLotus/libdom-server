package com.server.service;

import java.util.Optional;

import org.bson.types.ObjectId;
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
        } catch (Error e) {
            throw new Exception("Failed to register user with error: ", e);
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

    public Boolean updatePassword(ObjectId userId, String password) throws Exception {
        try {
            Optional<Users> optionalUser = this.userRepository.findById(userId);

            if (!optionalUser.isPresent()) {
                throw new Exception("User not found");
            }

            final Users user = optionalUser.get();

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            String encrpyedPassword = bcrypt.encode(password);
            user.setPassword(encrpyedPassword);
            this.userRepository.save(user);
            // Update password and return true
            return true;

        } catch (IllegalArgumentException e) {
            throw new Exception("Incorrect value for userId ", e);
        } catch (Error e) {
            throw new Exception("Failed to identify user", e);
        }
    }

}
