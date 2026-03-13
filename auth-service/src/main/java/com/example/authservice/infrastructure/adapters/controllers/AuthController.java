package com.example.authservice.infrastructure.adapters.controllers;

import com.example.authservice.application.usecases.LoginUseCase;
import com.example.authservice.application.usecases.RegisterUserUseCase;
import com.example.authservice.infrastructure.adapters.controllers.dto.AuthResponse;
import com.example.authservice.infrastructure.adapters.controllers.dto.LoginRequest;
import com.example.authservice.infrastructure.adapters.controllers.dto.RegisterRequest;
import com.example.authservice.infrastructure.adapters.controllers.dto.UserResponse;
import com.example.authservice.infrastructure.adapters.controllers.mapper.AuthDtoMapper;
import com.example.authservice.infrastructure.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final AuthDtoMapper mapper;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase, AuthDtoMapper mapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponse<UserResponse>>> register(@Valid @RequestBody RegisterRequest request) {
        return registerUserUseCase.register(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponse<AuthResponse>>> login(@Valid @RequestBody LoginRequest request) {
        return loginUseCase.login(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }
}
