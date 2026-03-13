package com.example.taskservice.application.usecases;

import com.example.taskservice.application.usecases.command.CreateTaskCommand;
import com.example.taskservice.domain.entities.Task;
import reactor.core.publisher.Mono;

public interface CreateTaskUseCase {
    Mono<Task> create(CreateTaskCommand command);
}
