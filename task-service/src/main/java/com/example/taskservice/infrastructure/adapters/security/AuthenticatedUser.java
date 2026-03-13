package com.example.taskservice.infrastructure.adapters.security;

import com.example.taskservice.domain.entities.Role;

public record AuthenticatedUser(Long userId, String username, Role role) {
}
