package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.clients.KeycloakClient;
import org.example.configurations.security.JwtConverterProperties;
import org.example.models.dao.DwhResponse;
import org.example.repositories.DwhRepository;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DwhControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KeycloakClient keycloakClient;

    @Autowired
    private DwhRepository dwhRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtConverterProperties jwtConverterProperties;

    private String baseUrl;

    private final TestRestTemplate restTemplate;

    private String authToken;

    @Autowired
    public DwhControllerTest(TestRestTemplate testRestTemplate) {
        this.restTemplate = testRestTemplate;
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        baseUrl = "http://localhost:" + randomServerPort + "/api/v1/admin";

        var tokens = keycloakClient.auth(jwtConverterProperties.getResourceId(),
                jwtConverterProperties.getUsername(),
                jwtConverterProperties.getPassword()).block();

        var objectMapper = new ObjectMapper();

        var jsonNode = objectMapper.readTree(tokens);
        authToken = jsonNode.get("access_token").toString().replace("\"", "");

        assertNotNull(authToken);
    }

    @Test
    public void testGenerateDwh() throws JsonProcessingException {
        int count = 5;

        var headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        var requestEntity = new HttpEntity<>(null, headers);

        var response = restTemplate.postForEntity(
                baseUrl + "/generate_dwh_role?count={count}",
                requestEntity,
                List.class,
                count
        );

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<DwhResponse> dwhResponses = response.getBody();
        assertNotNull(dwhResponses);
        assertEquals(count, dwhResponses.size());

        for (DwhResponse dwhResponse : dwhResponses) {
            assertNotNull(dwhResponse);
        }
    }

    @Test
    public void testGenerateDwhByZero() throws JsonProcessingException {
        int count = 0;

        var headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        var requestEntity = new HttpEntity<>(null, headers);

        var response = restTemplate.postForEntity(
                baseUrl + "/generate_dwh_role?count={count}",
                requestEntity,
                List.class,
                count
        );

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<DwhResponse> dwhResponses = response.getBody();
        assertNotNull(dwhResponses);
        assertEquals(count, dwhResponses.size());

        for (DwhResponse dwhResponse : dwhResponses) {
            assertNotNull(dwhResponse);
        }
    }

    @Test
    public void testGenerateDwhWithoutToken() throws JsonProcessingException {
        int count = 5;

        var response = restTemplate.postForEntity(
                baseUrl + "/generate_dwh_role?count={count}",
                null,
                List.class,
                count
        );

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @AfterEach
    public void resetDb() {
        dwhRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
