package org.example.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.configurations.AppSettings;
import org.example.models.atlas.AtlasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Component
public class KeycloakClient {
    private final WebClient keycloakClient;

    private final AppSettings appSettings;

    private static final String ROLE_ENDPOINT = "/add_role";

    @Autowired
    public KeycloakClient(@Qualifier("keycloakClient") WebClient keycloakClient, AppSettings appSettings) {
        this.keycloakClient = keycloakClient;
        this.appSettings = appSettings;
    }

    public Mono<String> AddNewRole(Map<String, AtlasRole> roles) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var jsonRoles = objectMapper.writeValueAsString(roles);

        return keycloakClient
                .post()
                .uri(ROLE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonRoles))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
