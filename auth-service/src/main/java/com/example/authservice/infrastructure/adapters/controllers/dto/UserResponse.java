package com.example.authservice.infrastructure.adapters.controllers.dto;

import com.example.authservice.domain.entities.Role;

public record UserResponse(Long id, String username, Role role) {
}
