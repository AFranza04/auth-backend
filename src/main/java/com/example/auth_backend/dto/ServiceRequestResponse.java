package com.example.auth_backend.dto;

import java.time.LocalDateTime;

public class ServiceRequestResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime dateCreated;
    private String createdBy;

    public ServiceRequestResponse(Long id, String title, String description, String category,
                                   LocalDateTime dateCreated, String createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public String getCreatedBy() { return createdBy; }
}