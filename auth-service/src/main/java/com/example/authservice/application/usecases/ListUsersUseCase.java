package com.example.authservice.application.usecases;

import com.example.authservice.domain.entities.User;
import reactor.core.publisher.Flux;

public interface ListUsersUseCase {
    Flux<User> listAll();
}
