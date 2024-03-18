package org.example.services;

import org.example.models.entities.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User GetUserById(UUID uuid){
        return userRepository.getById(uuid);
    }
}
