package com.example.taskservice.application.usecases;

import com.example.taskservice.domain.entities.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetTaskUseCase {
    Flux<Task> getAll();

    Mono<Task> getById(Long id);
}
