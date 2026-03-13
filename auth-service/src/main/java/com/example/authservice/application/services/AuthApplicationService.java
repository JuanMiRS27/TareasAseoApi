package com.example.authservice.application.services;

import com.example.authservice.application.usecases.LoginUseCase;
import com.example.authservice.application.usecases.RegisterUserUseCase;
import com.example.authservice.application.usecases.command.LoginCommand;
import com.example.authservice.application.usecases.command.RegisterUserCommand;
import com.example.authservice.application.usecases.result.LoginResult;
import com.example.authservice.domain.entities.Role;
import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.ports.PasswordEncoderPort;
import com.example.authservice.domain.ports.TokenProviderPort;
import com.example.authservice.domain.ports.UserRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AuthApplicationService implements RegisterUserUseCase, LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    public AuthApplicationService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenProviderPort tokenProviderPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public Mono<User> register(RegisterUserCommand command) {
        return userRepositoryPort.findByUsername(command.username())
                .flatMap(existing -> Mono.<User>error(new ResponseStatusException(HttpStatus.CONFLICT, "username ya existe")))
                .switchIfEmpty(userRepositoryPort.save(
                        new User(
                                null,
                                command.username(),
                                passwordEncoderPort.encode(command.password()),
                                Role.USER
                        )
                ));
    }

    @Override
    public Mono<LoginResult> login(LoginCommand command) {
        return userRepositoryPort.findByUsername(command.username())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "credenciales invalidas")))
                .filter(user -> passwordEncoderPort.matches(command.password(), user.password()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "credenciales invalidas")))
                .map(user -> new LoginResult(
                        tokenProviderPort.generateToken(user.id(), user.username(), user.role()),
                        "Bearer",
                        3600,
                        user.username(),
                        user.role()
                ));
    }
}


