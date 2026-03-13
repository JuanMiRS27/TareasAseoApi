package com.example.taskservice.infrastructure.adapters.controllers.dto;

import com.example.taskservice.domain.entities.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long assignedUserId,
        LocalDateTime createdAt
) {
}
