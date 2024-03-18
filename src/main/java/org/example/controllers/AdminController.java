package org.example.controllers;

import org.example.models.dao.DwhResponse;
import org.example.services.DwhService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dwh")
public class AdminController {
    private final DwhService dwhService;
    private final UserService userService;

    @Autowired
    public AdminController(DwhService dwhService, UserService userService) {
        this.dwhService = dwhService;
        this.userService = userService;
    }

    @GetMapping("/generate_dwh_role")
    public ResponseEntity<DwhResponse> generateDwh() {
        dwhService.generateDwh();

        return ResponseEntity.ok(new DwhResponse());
    }
}
