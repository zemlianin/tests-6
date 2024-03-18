package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.entities.Dwh;
import org.example.services.DwhService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RoleController {
    private DwhService dwhService;
    private UserService userService;

    @Autowired
    public RoleController(DwhService dwhService, UserService userService) {
        this.dwhService = dwhService;
        this.userService = userService;
    }

    @GetMapping("/ping")
    public UUID ping() throws JsonProcessingException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt)(authentication.getCredentials());
        String uuid = jwt.getClaim("sub");

        return UUID.fromString(uuid);
    }

    @PostMapping("/add_dwh")
    public ResponseEntity<Dwh> add_dwh() throws JsonProcessingException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt)(authentication.getCredentials());
        var uuid = UUID.fromString(jwt.getClaim("sub"));

        var user = userService.GetUserById(uuid);
        var dwh = dwhService.LinkDwh(user);

        return new ResponseEntity<>(new Dwh(), HttpStatus.CREATED);
    }

    @GetMapping("/get_all")
    public UUID GetType(@RequestParam(value = "id") UUID id) {
        return id;
    }
}
