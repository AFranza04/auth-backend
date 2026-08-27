package com.example.auth_backend.controller;

import com.example.auth_backend.dto.ServiceRequestDto;
import com.example.auth_backend.dto.ServiceRequestResponse;
import com.example.auth_backend.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ServiceRequestController {

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponse> create(@Valid @RequestBody ServiceRequestDto dto,
                                                           Authentication authentication) {
        String username = authentication.getName();
        ServiceRequestResponse response = service.create(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponse>> getAll(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(service.getAllForUser(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> getOne(@PathVariable Long id,
                                                           Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(service.getOneForUser(id, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestResponse> update(@PathVariable Long id,
                                                           @Valid @RequestBody ServiceRequestDto dto,
                                                           Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(service.update(id, dto, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        service.delete(id, username);
        return ResponseEntity.noContent().build();
    }
}