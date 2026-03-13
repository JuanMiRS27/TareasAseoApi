package com.example.taskservice.application.usecases;

import com.example.taskservice.application.usecases.command.UpdateTaskCommand;
import com.example.taskservice.domain.entities.Task;
import reactor.core.publisher.Mono;

public interface UpdateTaskUseCase {
    Mono<Task> update(Long taskId, UpdateTaskCommand command);
}
