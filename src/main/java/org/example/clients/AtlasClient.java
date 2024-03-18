package org.example.clients;
import org.example.configurations.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class AtlasClient {
    private final WebClient atlasClient;

    private final AppSettings appSettings;

    @Autowired
    public AtlasClient(@Qualifier("atlasClient") WebClient atlasClient, AppSettings appSettings) {
        this.atlasClient = atlasClient;
        this.appSettings = appSettings;
    }

    public Mono<String> GetAllTypes() {
        return atlasClient
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
