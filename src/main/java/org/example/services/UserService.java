package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clients.KeycloakClient;
import org.example.configurations.security.JwtConverterProperties;
import org.example.models.dao.RoleRequest;
import org.example.models.entities.User;
import org.example.repositories.UserRepository;
import org.springframework.security.oauth2.jose.JwaAlgorithm;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakClient keycloakClient;
    private final JwtConverterProperties jwtConverterProperties;

    public UserService(UserRepository userRepository,
                       KeycloakClient keycloakClient,
                       JwtConverterProperties jwtConverterProperties) {
        this.userRepository = userRepository;
        this.keycloakClient = keycloakClient;
        this.jwtConverterProperties = jwtConverterProperties;
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

    public User getOrCreateUserByUsername(String name) throws JsonProcessingException {
        var tokens = keycloakClient.auth(jwtConverterProperties.getResourceId(),
                jwtConverterProperties.getUsername(),
                jwtConverterProperties.getPassword()).block();

        var objectMapper = new ObjectMapper();

        var jsonNode = objectMapper.readTree(tokens);
        var accessToken = jsonNode.get("access_token").toString().replace("\"", "");

        var userJson = keycloakClient.getUserByUsername(accessToken, name).block();
        var userJsonNode = objectMapper.readTree(userJson);
        var userId = userJsonNode.get(0).get("id").toString();

       return getOrCreateUserById(UUID.fromString(userId));
    }


    public User addUser(User user){
        return userRepository.save(user);
    }
}
