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
public class AtlasAgentClient {
    private final WebClient atlasAgentClient;

    private final WebClient atlasAgentRestartClient;

    private final AppSettings appSettings;

    private static final String ROLE_ENDPOINT = "/add_role";
    private static final String RESTART_ENDPOINT = "/commit";

    @Autowired
    public AtlasAgentClient(@Qualifier("atlasAgentClient") WebClient atlasAgentClient,
                            @Qualifier("atlasAgentRestartClient") WebClient atlasAgentRestartClient,
                            AppSettings appSettings) {
        this.atlasAgentRestartClient = atlasAgentRestartClient;
        this.atlasAgentClient = atlasAgentClient;
        this.appSettings = appSettings;
    }

    public Mono<String> AddNewRole(Map<String, AtlasRole> roles) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var jsonRoles = objectMapper.writeValueAsString(roles);

        return atlasAgentClient
                .post()
                .uri(ROLE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonRoles))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }

    public Mono<String> restartAtlas(){
        return atlasAgentRestartClient
                .get()
                .uri(RESTART_ENDPOINT)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(1,
                        Duration.ofMillis(300000)));
    }
}
