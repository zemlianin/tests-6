package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.models.dao.DwhResponse;
import org.example.services.DwhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final DwhService dwhService;

    @Autowired
    public AdminController(DwhService dwhService) {
        this.dwhService = dwhService;
    }

    @PostMapping("/generate_dwh_role")
    public ResponseEntity<DwhResponse> generateDwh() throws JsonProcessingException {
        try {
            var dwh = dwhService.generateDwh(1).get(0);

            return new ResponseEntity<>(new DwhResponse(dwh), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            System.out.println("Error of Json Processing");
            throw e;
        }
    }
}
