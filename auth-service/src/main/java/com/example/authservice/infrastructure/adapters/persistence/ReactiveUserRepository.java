package com.example.authservice.infrastructure.adapters.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ReactiveUserRepository extends ReactiveCrudRepository<UserR2dbcEntity, Long> {
    Mono<UserR2dbcEntity> findByUsername(String username);
}
