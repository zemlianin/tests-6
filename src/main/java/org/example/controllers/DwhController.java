package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysql.cj.exceptions.WrongArgumentException;
import org.example.models.dao.DwhResponse;
import org.example.models.enums.PermissionLevel;
import org.example.services.DwhService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dwh")
public class DwhController {
    private final DwhService dwhService;
    private final UserService userService;

    @Autowired
    public DwhController(DwhService dwhService, UserService userService) {
        this.dwhService = dwhService;
        this.userService = userService;
    }

    @GetMapping("/ping")
    public Object ping() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities;
    }

    @PostMapping("/add_dwh")
    public ResponseEntity<DwhResponse> addDwh() {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            var jwt = (Jwt)(authentication.getCredentials());
            var uuid = UUID.fromString(jwt.getClaim("sub"));

            var user = userService.getOrCreateUserById(uuid);
            var dwh = dwhService.linkDwh(user);
            return new ResponseEntity<>(new DwhResponse(dwh), HttpStatus.CREATED);
        } catch (WrongArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add_user_to_dwh")
    public ResponseEntity<DwhResponse> addUserToDwh(@RequestParam String username, @RequestParam String permissionLevel) throws JsonProcessingException {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            var jwt = (Jwt)(authentication.getCredentials());
            var uuid = UUID.fromString(jwt.getClaim("sub"));
            var dwhOwner = userService.getOrCreateUserById(uuid);
            var user = userService.getOrCreateUserByUsername(username);

            var dwh = dwhService.linkUserToExistDwh(user, dwhOwner, PermissionLevel.valueOf(permissionLevel) );
            return new ResponseEntity<>(new DwhResponse(dwh), HttpStatus.OK);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (WrongArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
