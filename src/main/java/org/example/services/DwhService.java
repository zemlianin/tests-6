package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.exceptions.WrongArgumentException;
import org.example.clients.AtlasAgentClient;
import org.example.clients.KeycloakClient;
import org.example.configurations.AppSettings;
import org.example.configurations.security.JwtConverterProperties;
import org.example.models.atlas.AtlasRole;
import org.example.models.dao.RoleRequest;
import org.example.models.entities.Dwh;
import org.example.models.entities.User;
import org.example.repositories.DwhRepository;
import org.example.repositories.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.RejectedExecutionException;

@Service
public class DwhService {
    private final DwhRepository dwhRepository;
    private final UserRepository userRepository;
    private final AtlasAgentClient atlasAgentClient;
    private final AtlasRoleFactory atlasRoleFactory;
    private final KeycloakClient keycloakClient;
    private final JwtConverterProperties jwtConverterProperties;
    private final AppSettings appSettings;

    public DwhService(DwhRepository dwhRepository,
                      UserRepository userRepository,
                      AtlasAgentClient atlasAgentClient,
                      AtlasRoleFactory atlasRoleFactory,
                      KeycloakClient keycloakClient,
                      JwtConverterProperties jwtConverterProperties,
                      AppSettings appSettings) {
        this.userRepository = userRepository;
        this.dwhRepository = dwhRepository;
        this.atlasAgentClient = atlasAgentClient;
        this.atlasRoleFactory = atlasRoleFactory;
        this.keycloakClient = keycloakClient;
        this.jwtConverterProperties = jwtConverterProperties;
        this.appSettings = appSettings;

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Dwh linkDwh(User user) {
        if (user.getDwh() != null) {
            throw new WrongArgumentException("User already has DWH");
        }

        var dwhList = dwhRepository.findAllByIsUsedFalse();

        if (dwhList.isEmpty()) {
            throw new NoSuchElementException("All dwh were used");
        }

        var dwh = dwhList.stream().findFirst().get();
        dwhRepository.softUsedByName(dwh.getName());
        userRepository.softDwhByUserId(user.getId(), dwh);

        if (!tryLinkRoleInKeycloak(user, dwh.getName())) {
            throw new RejectedExecutionException();
        }

        return dwh;
    }


    public List<Dwh> getAll() {
        return dwhRepository.findAll();
    }

    public List<Dwh> generateDwh(int count) throws JsonProcessingException {
        var map = new TreeMap<String, AtlasRole>();
        ArrayList<Dwh> list = new ArrayList<Dwh>();

        for (int i = 0; i < count; i++){
            var dwhName = generateNewDwhName();
            var allPermissionRegex = "^(?=" + dwhName + ").+";
            var readPermissionsRegex = "^(?!DWH_).+";
            var role = atlasRoleFactory.createDefaultDwhRole(
                    allPermissionRegex,
                    readPermissionsRegex);

            var objectMapper = new ObjectMapper();
            var jsonRole = objectMapper.writeValueAsString(role);

            System.out.println(jsonRole);
            map.put(dwhName, role);

            var dwh = new Dwh();
            dwh.setName(dwhName);
            dwh.setUsed(false);

            list.add(dwh);
        }

        atlasAgentClient.AddNewRole(map)
                .doOnNext(response -> {
                    System.out.println("Answer of server: " + response);
                })
                .block();

        return dwhRepository.saveAll(list);
    }

    private Boolean tryLinkRoleInKeycloak(User user, String role) {
        try {
            var tokens = keycloakClient.auth(jwtConverterProperties.getResourceId(),
                    jwtConverterProperties.getUsername(),
                    jwtConverterProperties.getPassword()).block();

            var objectMapper = new ObjectMapper();

            var jsonNode = objectMapper.readTree(tokens);
            var accessToken = jsonNode.get("access_token").toString().replace("\"", "");
            keycloakClient.createRole(accessToken, new RoleRequest(role, "", false)).block();

            var keycloakRole = keycloakClient.getRoleByName(accessToken, role).block();

            keycloakClient.linkRoleWithUser(accessToken, user, keycloakRole).block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateNewDwhName() {
        try {
            var random = new Random();
            var sha = generateSHA256(Long.toString(random.nextLong()));
            var name = "DWH_" + sha.substring(1, 6) + "_";
            return name;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-256 not found");
            throw new RuntimeException();
        }
    }

    public static String generateSHA256(String input) throws NoSuchAlgorithmException {
        var digest = MessageDigest.getInstance("SHA-256");

        var hash = digest.digest(input.getBytes());

        var hexString = new StringBuilder();
        for (byte b : hash) {
            var hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Scheduled(cron = "${interval-in-cron}")
    public void dailyGenerateNewRoles() throws JsonProcessingException {
        var allDwh = dwhRepository.findAll();
        var notUsingDwh = dwhRepository.findAllByIsUsedFalse();

        if(notUsingDwh.size() / (double)allDwh.size() > appSettings.rolesNotUsePart){
            return;
        }
        var count = allDwh.size();
        generateDwh(count);

        atlasAgentClient.restartAtlas().block();
    }
}
