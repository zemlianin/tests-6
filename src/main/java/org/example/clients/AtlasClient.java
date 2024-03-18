package org.example.clients;
import org.example.configurations.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class AtlasClient {
    private final WebClient webClient;

    private final AppSettings appSettings;

    @Autowired
    public AtlasClient(WebClient webClient, AppSettings appSettings) {
        this.webClient = webClient;
        this.appSettings = appSettings;
    }

    public Mono<String> GetAllTypes() {
        return webClient
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(appSettings.retryAttempts,
                        Duration.ofMillis(appSettings.retryDelayMillis)));
    }
}
