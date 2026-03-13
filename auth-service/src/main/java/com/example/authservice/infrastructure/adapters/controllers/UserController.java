package com.example.authservice.infrastructure.adapters.controllers;

import com.example.authservice.application.usecases.ListUsersUseCase;
import com.example.authservice.infrastructure.adapters.controllers.dto.UserResponse;
import com.example.authservice.infrastructure.adapters.controllers.mapper.AuthDtoMapper;
import com.example.authservice.infrastructure.shared.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ListUsersUseCase listUsersUseCase;
    private final AuthDtoMapper mapper;

    public UserController(ListUsersUseCase listUsersUseCase, AuthDtoMapper mapper) {
        this.listUsersUseCase = listUsersUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<java.util.List<UserResponse>>>> listAll() {
        return listUsersUseCase.listAll()
                .map(mapper::toResponse)
                .collectList()
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }
}
