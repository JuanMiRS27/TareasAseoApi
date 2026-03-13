package com.example.taskservice.application.usecases.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTaskCommand(
        @NotBlank(message = "title es obligatorio")
        @Size(min = 3, max = 150, message = "title debe tener entre 3 y 150 caracteres")
        String title,
        @Size(max = 1000, message = "description maximo 1000 caracteres")
        String description,
        @NotNull(message = "assignedUserId es obligatorio")
        Long assignedUserId
) {
}


