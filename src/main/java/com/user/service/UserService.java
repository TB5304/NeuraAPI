package com.user.service;

import com.user.model.User;
import com.user.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public User createUser(User user) {
        user.setId(String.valueOf(sequenceGeneratorService.getNextSequence(User.SEQUENCE_NAME)));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public User updateUser(String id, User updatedUser) {
        return userRepo.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setCity(updatedUser.getCity());
            existingUser.setState(updatedUser.getState());
            existingUser.setCountry(updatedUser.getCountry());
            existingUser.setUpdatedAt(LocalDateTime.now());  // Update timestamp
            return userRepo.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User Not Found!"));
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}
