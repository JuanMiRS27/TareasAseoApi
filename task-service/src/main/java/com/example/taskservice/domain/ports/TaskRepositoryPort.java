package com.example.taskservice.domain.ports;

import com.example.taskservice.domain.entities.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepositoryPort {
    Mono<Task> save(Task task);

    Mono<Task> findById(Long id);

    Flux<Task> findAll();

    Mono<Void> deleteById(Long id);
}
