package com.example.authservice.application.usecases.result;

import com.example.authservice.domain.entities.Role;

public record LoginResult(String token, String tokenType, long expiresInSeconds, String username, Role role) {
}
