package com.example.taskservice.infrastructure.shared;

import java.util.Map;

public record ApiError(
        String code,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
