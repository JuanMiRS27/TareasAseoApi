package com.example.authservice.application.services;

import com.example.authservice.application.usecases.ListUsersUseCase;
import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserApplicationService implements ListUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserApplicationService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public Flux<User> listAll() {
        return userRepositoryPort.findAll();
    }
}
