package org.example.services;

import org.example.clients.AtlasAgentClient;
import org.example.clients.KeycloakClient;
import org.example.configurations.AppSettings;
import org.example.configurations.security.JwtConverterProperties;
import org.example.repositories.DwhRepository;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class DwhServiceTest {

    public DwhService dwhService;

    @Mock
    private DwhRepository dwhRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AtlasAgentClient atlasAgentClient;

    @Mock
    private AtlasRoleFactory atlasRoleFactory;

    @Mock
    private KeycloakClient keycloakClient;

    @Mock
    private JwtConverterProperties jwtConverterProperties;

    @Mock
    private AppSettings appSettings;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dwhService = new DwhService(
                dwhRepository,
                userRepository,
                roleRepository,
                atlasAgentClient,
                atlasRoleFactory,
                keycloakClient,
                jwtConverterProperties,
                appSettings
        );
    }

    @Test
    public void testGenerateNewDwhName() throws NoSuchAlgorithmException {
        var randomMock = mock(Random.class);

        when(randomMock.nextLong()).thenReturn(123456789L);

        String dwhName = dwhService.generateNewDwhName();

        assertTrue(Pattern.matches("DWH_[0-9a-f]{5}_", dwhName));
    }

    @Test
    public void testGenerateSHA256() throws NoSuchAlgorithmException {
        var digestMock = mock(MessageDigest.class);

        String input = "test_input";

        String expectedHash = "952822de6a627ea459e1e7a8964191c79fccfb14ea545d93741b5cf3ed71a09a";

        String actualHash = dwhService.generateSHA256(input);

        assertEquals(expectedHash, actualHash);
    }
}
