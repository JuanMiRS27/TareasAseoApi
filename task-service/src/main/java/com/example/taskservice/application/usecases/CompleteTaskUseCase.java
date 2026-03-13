package com.example.taskservice.application.usecases;

import com.example.taskservice.application.usecases.command.CompleteTaskCommand;
import com.example.taskservice.domain.entities.Task;
import reactor.core.publisher.Mono;

public interface CompleteTaskUseCase {
    Mono<Task> complete(Long taskId, CompleteTaskCommand command);
}
