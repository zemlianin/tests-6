package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clients.AtlasClient;
import org.example.clients.KeycloakClient;
import org.example.configurations.security.JwtConverterProperties;
import org.example.models.entities.AtlasType;
import org.example.repositories.AtlasTypeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


// График где по оси игрик число существующих типов в изолированной подсистеме
// График где по оси игрик число добавленных экземпляров за час
@Service
public class MetaMonitoringService {
    private final AtlasClient atlasClient;
    private final JwtConverterProperties jwtConverterProperties;
    private final KeycloakClient keycloakClient;
    private final AtlasTypeRepository atlasTypeRepository;

    private static Long lastRunTimestamp;

    public MetaMonitoringService(AtlasClient atlasClient,
                                 JwtConverterProperties jwtConverterProperties,
                                 KeycloakClient keycloakClient,
                                 AtlasTypeRepository atlasTypeRepository) {
        this.atlasClient = atlasClient;
        this.jwtConverterProperties = jwtConverterProperties;
        this.keycloakClient = keycloakClient;
        this.atlasTypeRepository = atlasTypeRepository;
        lastRunTimestamp = 0l;
    }

    @Scheduled(cron = "${monitoring-interval-in-cron}")
    public void dailyGenerateNewRoles() throws JsonProcessingException {
        var tokens = keycloakClient.auth(jwtConverterProperties.getResourceId(),
                jwtConverterProperties.getUsername(),
                jwtConverterProperties.getPassword()).block();

        var objectMapper = new ObjectMapper();

        var jsonNode = objectMapper.readTree(tokens);
        var accessToken = jsonNode.get("access_token").toString().replace("\"", "");
        var types = atlasClient.GetAllTypes(accessToken).block();

        for(var type : types.getEntityDefs()){
            if(lastRunTimestamp < Long.parseLong(type.getUpdateTime())){
                atlasTypeRepository.save(new AtlasType(type));
            }
        }
    }
}
