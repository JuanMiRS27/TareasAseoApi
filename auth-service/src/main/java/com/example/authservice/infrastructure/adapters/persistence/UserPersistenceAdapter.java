package com.example.authservice.infrastructure.adapters.persistence;

import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final ReactiveUserRepository reactiveUserRepository;
    private final UserPersistenceMapper mapper;

    public UserPersistenceAdapter(ReactiveUserRepository reactiveUserRepository, UserPersistenceMapper mapper) {
        this.reactiveUserRepository = reactiveUserRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<User> save(User user) {
        return reactiveUserRepository.save(mapper.toEntity(user)).map(mapper::toDomain);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return reactiveUserRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public Mono<User> findById(Long id) {
        return reactiveUserRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<User> findAll() {
        return reactiveUserRepository.findAll().map(mapper::toDomain);
    }
}
