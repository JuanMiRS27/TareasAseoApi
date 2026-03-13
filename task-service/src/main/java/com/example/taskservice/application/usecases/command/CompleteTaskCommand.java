package com.example.taskservice.application.usecases.command;

import com.example.taskservice.domain.entities.Role;

public record CompleteTaskCommand(Long actorUserId, Role actorRole) {
}
