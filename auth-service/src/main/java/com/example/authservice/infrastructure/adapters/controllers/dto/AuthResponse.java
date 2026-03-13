package com.example.authservice.infrastructure.adapters.controllers.dto;

import com.example.authservice.domain.entities.Role;

public record AuthResponse(
        String token,
        String tokenType,
        long expiresInSeconds,
        String username,
        Role role
) {
}
