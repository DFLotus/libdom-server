package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
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
            Users existingUser = this.userRepository.findByEmail(user.getEmail());

            if (existingUser != null) {
                throw new Exception("Email already exists");
            }

            return this.userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new Exception("Email already exists", e);
        }
    }

}
