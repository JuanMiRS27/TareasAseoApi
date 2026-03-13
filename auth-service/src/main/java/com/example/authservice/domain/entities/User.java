package com.example.authservice.domain.entities;

public record User(Long id, String username, String password, Role role) {
}
