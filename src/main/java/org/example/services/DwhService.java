package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.exceptions.WrongArgumentException;
import org.example.clients.AtlasAgentClient;
import org.example.models.entities.Dwh;
import org.example.models.entities.User;
import org.example.repositories.DwhRepository;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class DwhService {
    private final DwhRepository dwhRepository;
    private final UserRepository userRepository;
    private final AtlasAgentClient atlasAgentClient;
    private final AtlasRoleFactory atlasRoleFactory;

    public DwhService(DwhRepository dwhRepository,
                      UserRepository userRepository,
                      AtlasAgentClient atlasAgentClient,
                      AtlasRoleFactory atlasRoleFactory) {
        this.userRepository = userRepository;
        this.dwhRepository = dwhRepository;
        this.atlasAgentClient = atlasAgentClient;
        this.atlasRoleFactory = atlasRoleFactory;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Dwh linkDwh(User user) {
        if (user.getDwh() != null) {
            throw new WrongArgumentException("User already has DWH");
        }

        var dwhList = dwhRepository.findFirstByIsUsedFalse();

        if (dwhList.isEmpty()) {
            throw new NoSuchElementException("All dwh were used");
        }
        var dwh = dwhList.stream().findFirst().get();
        dwhRepository.softUsedByName(dwh.getName());
        userRepository.softDwhByUserId(user.getId(), dwh);
        return dwh;
    }

    public List<Dwh> getAll() {
        return dwhRepository.findAll();
    }

    public Dwh generateDwh() throws JsonProcessingException {
        var dwhName = generateNewDwhName();
        var allPermissionRegex = "^(?=" + dwhName + ").+";
        var readPermissionsRegex = "^(?!DWH_).+";
        var role = atlasRoleFactory.createDefaultDwhRole(
                allPermissionRegex,
                readPermissionsRegex);

        var objectMapper = new ObjectMapper();
        var jsonRole = objectMapper.writeValueAsString(role);

        System.out.println(jsonRole);
       /* atlasAgentClient.AddNewRole(role)
                .doOnNext(response -> {
                    System.out.println("Answer of server: " + response);
                })
                .block();*/
        return new Dwh();
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
}
