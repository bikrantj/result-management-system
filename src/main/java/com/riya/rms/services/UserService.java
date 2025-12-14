package com.riya.rms.services;

import com.riya.rms.models.User;
import com.riya.rms.repositories.UserRepository;
import com.riya.rms.utils.Role;

import java.util.List;

public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository repo) {
        this.userRepo = repo;
    }

    public boolean isUsernameTaken(String username) {
        return userRepo.findByUsername(username) != null;
    }

    public List<User> findAll(Role role) {
        List<User> users = userRepo.findAll();
        return users.stream()
                .filter(user -> user.getRole() != null && user.getRole().equals(role))
                .toList();
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


}
