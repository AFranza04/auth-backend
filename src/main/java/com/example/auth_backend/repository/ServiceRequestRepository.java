package com.example.auth_backend.repository;

import com.example.auth_backend.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCreatedBy(String createdBy);
    Optional<ServiceRequest> findByIdAndCreatedBy(Long id, String createdBy);
}