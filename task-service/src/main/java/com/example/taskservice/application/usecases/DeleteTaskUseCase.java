package com.example.taskservice.application.usecases;

import reactor.core.publisher.Mono;

public interface DeleteTaskUseCase {
    Mono<Void> delete(Long taskId);
}
