package com.example.authservice.infrastructure.adapters.controllers.mapper;

import com.example.authservice.application.usecases.command.LoginCommand;
import com.example.authservice.application.usecases.command.RegisterUserCommand;
import com.example.authservice.application.usecases.result.LoginResult;
import com.example.authservice.domain.entities.User;
import com.example.authservice.infrastructure.adapters.controllers.dto.AuthResponse;
import com.example.authservice.infrastructure.adapters.controllers.dto.LoginRequest;
import com.example.authservice.infrastructure.adapters.controllers.dto.RegisterRequest;
import com.example.authservice.infrastructure.adapters.controllers.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthDtoMapper {

    public RegisterUserCommand toCommand(RegisterRequest request) {
        return new RegisterUserCommand(request.username(), request.password());
    }

    public LoginCommand toCommand(LoginRequest request) {
        return new LoginCommand(request.username(), request.password());
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.username(), user.role());
    }

    public AuthResponse toResponse(LoginResult loginResult) {
        return new AuthResponse(
                loginResult.token(),
                loginResult.tokenType(),
                loginResult.expiresInSeconds(),
                loginResult.username(),
                loginResult.role()
        );
    }
}
