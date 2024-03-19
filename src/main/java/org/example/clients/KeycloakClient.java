package org.example.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.configurations.AppSettings;
import org.example.models.dao.KeycloakRole;
import org.example.models.dao.RoleRequest;
import org.example.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Component
public class KeycloakClient {
    private final WebClient keycloakClient;

    private final AppSettings appSettings;

    private static final String AUTH_ENDPOINT = "/realms/auth/protocol/openid-connect/token";
    private static final String ADD_ROLE_ENDPOINT = "/admin/realms/auth/roles";
    private static final String GET_ROLE_ENDPOINT = "/admin/realms/auth/roles";

    @Autowired
    public KeycloakClient(@Qualifier("keycloakClient") WebClient keycloakClient, AppSettings appSettings) {
        this.keycloakClient = keycloakClient;
        this.appSettings = appSettings;
    }

    public Mono<String> auth(String clientId, String username, String password) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var requestBody = "client_id=" + clientId +
                "&grant_type=password" +
                "&username=" + username +
                "&password=" + password;

        return keycloakClient
                .post()
                .uri(AUTH_ENDPOINT)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }

    public Mono<String> createRole(String adminAccessToken, RoleRequest request) throws JsonProcessingException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminAccessToken);

        var objectMapper = new ObjectMapper();

        var requestBody = objectMapper.writeValueAsString(request);

        return keycloakClient.post()
                .uri(ADD_ROLE_ENDPOINT)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }

    public Mono<String> linkRoleWithUser(String adminAccessToken, User user, List<KeycloakRole> role) throws JsonProcessingException {
        var uri = "/admin/realms/auth/users/" + user.getId() +
                "/role-mappings/realm";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminAccessToken);
        var objectMapper = new ObjectMapper();

        var requestBody = objectMapper.writeValueAsString(role);

        return keycloakClient
                .post()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }

    public Mono<List<KeycloakRole>> getRoleByName(String adminAccessToken, String name) throws JsonProcessingException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminAccessToken);

        var uriWithParams = UriComponentsBuilder.fromUriString(GET_ROLE_ENDPOINT)
                .queryParam("search", name)
                .build()
                .toUriString();

        return keycloakClient.get()
                .uri(uriWithParams)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<KeycloakRole>>() {})
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
