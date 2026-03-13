package com.example.taskservice.infrastructure.adapters.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tasks")
public class TaskR2dbcEntity {

    @Id
    @Column("id")
    private Long id;
    @Column("title")
    private String title;
    @Column("description")
    private String description;
    @Column("status")
    private String status;
    @Column("assigned_user_id")
    private Long assignedUserId;
    @Column("created_at")
    private LocalDateTime createdAt;

    public TaskR2dbcEntity() {
    }

    public TaskR2dbcEntity(Long id, String title, String description, String status, Long assignedUserId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUserId = assignedUserId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
