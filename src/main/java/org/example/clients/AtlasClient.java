package org.example.clients;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.configurations.AppSettings;
import org.example.models.atlas.AtlasTypeResponse;
import org.example.models.dao.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class AtlasClient {
    private final WebClient atlasClient;
    private final AppSettings appSettings;

    private static final String TYPE_ENDPOINT = "/types/typedefs";

    @Autowired
    public AtlasClient(@Qualifier("atlasClient") WebClient atlasClient, AppSettings appSettings) {
        this.atlasClient = atlasClient;
        this.appSettings = appSettings;
    }

    public Mono<AtlasTypeResponse> GetAllTypes(String adminAccessToken) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminAccessToken);

        return atlasClient.get()
                .uri(TYPE_ENDPOINT)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(AtlasTypeResponse.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
