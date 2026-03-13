package com.example.authservice.application.usecases;

import com.example.authservice.application.usecases.command.LoginCommand;
import com.example.authservice.application.usecases.result.LoginResult;
import reactor.core.publisher.Mono;

public interface LoginUseCase {
    Mono<LoginResult> login(LoginCommand command);
}
