package com.example.authservice.infrastructure.adapters.security;

import com.example.authservice.domain.entities.Role;

public record AuthenticatedUser(Long userId, String username, Role role) {
}
