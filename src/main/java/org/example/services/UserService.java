package org.example.services;

import org.example.models.entities.User;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrCreateUserById(UUID uuid){
        var userOptional = userRepository.findById(uuid);

        if(userOptional.isEmpty()){
            var newUser = new User(uuid);
            return addUser(newUser);
        } else {
            return userOptional.get();
        }
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
