package org.example.services;

import org.example.clients.KeycloakClient;
import org.example.configurations.security.JwtConverterProperties;
import org.example.models.entities.User;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetOrCreateUserById_UserExists() {
        MockitoAnnotations.initMocks(this);

        var keycloakClient = mock(KeycloakClient.class);
        var jwtConverterProperties = mock(JwtConverterProperties.class);
        var userService = new UserService(userRepository, keycloakClient, jwtConverterProperties);

        var existingUserId = UUID.randomUUID();
        var existingUser = new User(existingUserId);

        when(userRepository.findById(existingUserId)).thenReturn(Optional.of(existingUser));

        User resultUser = userService.getOrCreateUserById(existingUserId);

        verify(userRepository, times(1)).findById(existingUserId);

        assertEquals(existingUser, resultUser);
    }

    @Test
    public void testGetOrCreateUserById_UserNotExists() {
        MockitoAnnotations.initMocks(this);

        KeycloakClient keycloakClient = mock(KeycloakClient.class);
        JwtConverterProperties jwtConverterProperties = mock(JwtConverterProperties.class);

        UUID newUserId = UUID.randomUUID();
        User newUser = new User(newUserId);

        when(userRepository.findById(newUserId)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(newUser);

        UserService userService = new UserService(userRepository, keycloakClient, jwtConverterProperties);

        User resultUser = userService.getOrCreateUserById(newUserId);

        verify(userRepository, times(1)).findById(newUserId);

        verify(userRepository, times(1)).save(any());

        assertEquals(newUser, resultUser);
    }
}
