package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.models.dao.DwhResponse;
import org.example.services.DwhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final DwhService dwhService;

    @Autowired
    public AdminController(DwhService dwhService) {
        this.dwhService = dwhService;
    }

    @PostMapping("/generate_dwh_role")
    public ResponseEntity<List<DwhResponse>> generateDwh(@RequestParam Integer count) throws JsonProcessingException {
        try {
            var responseList = dwhService.generateDwh(count)
                    .stream()
                    .map(DwhResponse::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseList, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            System.out.println("Error of Json Processing");
            throw e;
        }
    }
}
