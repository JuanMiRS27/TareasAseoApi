package com.example.authservice.domain.ports;

import com.example.authservice.domain.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<User> save(User user);

    Mono<User> findByUsername(String username);

    Mono<User> findById(Long id);

    Flux<User> findAll();
}
