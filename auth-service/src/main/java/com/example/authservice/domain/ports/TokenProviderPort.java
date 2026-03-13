package com.example.authservice.domain.ports;

import com.example.authservice.domain.entities.Role;

public interface TokenProviderPort {
    String generateToken(Long userId, String username, Role role);
}
