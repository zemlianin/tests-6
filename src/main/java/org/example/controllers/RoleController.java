package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RoleController {
    @GetMapping("/ping")
    public String ping() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

            // Преобразование объекта в JSON-строку
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return objectMapper.writeValueAsString(authentication.getCredentials());
    }

    @GetMapping("/get_all")
    public UUID GetType(@RequestParam(value = "id") UUID id) {
        return id;
    }

    private UUID GetIdFromCredentials(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

            var rootNode = objectMapper.readTree(json);

            var idValue = rootNode.get("claims").get("sub").asText();

            if(idValue.isEmpty()){
                throw new IllegalArgumentException();
            }
            return UUID.fromString(idValue);
    }

}
