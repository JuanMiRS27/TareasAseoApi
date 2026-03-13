package com.example.authservice.application.usecases;

import com.example.authservice.application.usecases.command.RegisterUserCommand;
import com.example.authservice.domain.entities.User;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<User> register(RegisterUserCommand command);
}
