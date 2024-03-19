package org.example.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppSettings {

    @Value("${atlas.url}")
    public String atlasUrl;

    @Value("${atlas.agent.url}")
    public String atlasAgentUrl;

    @Value("${keycloak.url}")
    public String keycloakUrl;

    @Value("${atlas_client.timeout}")
    public int timeout;

    @Value("${atlas_client.retry_attempts}")
    public int retryAttempts;

    @Value("${atlas_client.retry_delay_millis}")
    public int retryDelayMillis;

    @Value("${roles.not.use.part}")
    public double rolesNotUsePart;
}