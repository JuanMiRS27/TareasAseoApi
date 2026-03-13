package com.example.authservice.application.usecases.command;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "username es obligatorio") String username,
        @NotBlank(message = "password es obligatorio") String password
) {
}
