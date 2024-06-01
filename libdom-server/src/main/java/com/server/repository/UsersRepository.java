package com.server.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.model.Users;

public interface UsersRepository extends MongoRepository<Users, ObjectId> {
    Users findByEmail(String email);
    Users findByUsername(String username);
}

/*by extending MongoRepository, UsersRepository inherits methods to create, read, update and delete a document*/