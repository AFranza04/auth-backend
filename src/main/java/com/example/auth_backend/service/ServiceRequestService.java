package com.example.auth_backend.service;

import com.example.auth_backend.dto.ServiceRequestDto;
import com.example.auth_backend.dto.ServiceRequestResponse;
import com.example.auth_backend.model.ServiceRequest;
import com.example.auth_backend.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public ServiceRequestResponse create(ServiceRequestDto dto, String username) {
        ServiceRequest request = new ServiceRequest();
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setCategory(dto.getCategory());
        request.setDateCreated(LocalDateTime.now());
        request.setCreatedBy(username); // taken from JWT, never from the request body

        ServiceRequest saved = repository.save(request);
        return toResponse(saved);
    }

    public List<ServiceRequestResponse> getAllForUser(String username) {
        return repository.findByCreatedBy(username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceRequestResponse getOneForUser(Long id, String username) {
        ServiceRequest request = repository.findByIdAndCreatedBy(id, username)
                .orElseThrow(() -> new IllegalStateException("Service request not found"));
        return toResponse(request);
    }

    public ServiceRequestResponse update(Long id, ServiceRequestDto dto, String username) {
        ServiceRequest request = repository.findByIdAndCreatedBy(id, username)
                .orElseThrow(() -> new IllegalStateException("Service request not found"));

        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setCategory(dto.getCategory());

        ServiceRequest updated = repository.save(request);
        return toResponse(updated);
    }

    public void delete(Long id, String username) {
        ServiceRequest request = repository.findByIdAndCreatedBy(id, username)
                .orElseThrow(() -> new IllegalStateException("Service request not found"));
        repository.delete(request);
    }

    private ServiceRequestResponse toResponse(ServiceRequest r) {
        return new ServiceRequestResponse(
                r.getId(), r.getTitle(), r.getDescription(), r.getCategory(),
                r.getDateCreated(), r.getCreatedBy()
        );
    }
}