package com.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.user.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
}
