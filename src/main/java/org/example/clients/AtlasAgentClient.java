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

@Component
public class AtlasAgentClient {
    private final WebClient atlasAgentClient;

    private final AppSettings appSettings;

    private static final String ROLE_ENDPOINT = "/add_role";

    @Autowired
    public AtlasAgentClient(@Qualifier("atlasAgentClient") WebClient atlasAgentClient, AppSettings appSettings) {
        this.atlasAgentClient = atlasAgentClient;
        this.appSettings = appSettings;
    }

    public Mono<String> AddNewRole(AtlasRole role) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var jsonRole = objectMapper.writeValueAsString(role);

        return atlasAgentClient
                .post()
                .uri(ROLE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonRole))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
