package com.example.taskservice.domain.entities;

import java.time.LocalDateTime;

public record Task(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long assignedUserId,
        LocalDateTime createdAt
) {
}
