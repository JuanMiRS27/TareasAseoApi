package com.example.taskservice.infrastructure.config;

import com.example.taskservice.infrastructure.shared.ApiError;
import com.example.taskservice.infrastructure.shared.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(WebExchangeBindException exception, ServerWebExchange exchange) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));

        ApiError error = new ApiError("VALIDATION_ERROR", "datos invalidos", exchange.getRequest().getPath().value(), errors);
        return ResponseEntity.badRequest().body(ApiResponse.error(error));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException exception, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        ApiError error = new ApiError(status.name(), exception.getReason(), exchange.getRequest().getPath().value(), null);
        return ResponseEntity.status(status).body(ApiResponse.error(error));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException exception, ServerWebExchange exchange) {
        ApiError error = new ApiError("FORBIDDEN", "acceso denegado", exchange.getRequest().getPath().value(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(error));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataAccess(DataAccessException exception, ServerWebExchange exchange) {
        log.error("Database error at {}: {}", exchange.getRequest().getPath().value(), exception.getMessage(), exception);
        ApiError error = new ApiError("DATABASE_ERROR", "error de base de datos", exchange.getRequest().getPath().value(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception exception, ServerWebExchange exchange) {
        log.error("Unhandled error at {}: {}", exchange.getRequest().getPath().value(), exception.getMessage(), exception);
        ApiError error = new ApiError("INTERNAL_ERROR", "error interno del servidor", exchange.getRequest().getPath().value(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(error));
    }
}


