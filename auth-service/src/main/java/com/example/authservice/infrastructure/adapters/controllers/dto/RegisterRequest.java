package com.example.authservice.infrastructure.adapters.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "username es obligatorio")
        @Size(min = 3, max = 100, message = "username debe tener entre 3 y 100 caracteres")
        String username,
        @NotBlank(message = "password es obligatorio")
        @Size(min = 6, max = 100, message = "password debe tener entre 6 y 100 caracteres")
        String password
) {
}
