package com.example.taskservice.application.services;

import com.example.taskservice.application.usecases.CompleteTaskUseCase;
import com.example.taskservice.application.usecases.CreateTaskUseCase;
import com.example.taskservice.application.usecases.DeleteTaskUseCase;
import com.example.taskservice.application.usecases.GetTaskUseCase;
import com.example.taskservice.application.usecases.UpdateTaskUseCase;
import com.example.taskservice.application.usecases.command.CompleteTaskCommand;
import com.example.taskservice.application.usecases.command.CreateTaskCommand;
import com.example.taskservice.application.usecases.command.UpdateTaskCommand;
import com.example.taskservice.domain.entities.Role;
import com.example.taskservice.domain.entities.Task;
import com.example.taskservice.domain.entities.TaskStatus;
import com.example.taskservice.domain.ports.TaskRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskApplicationService implements CreateTaskUseCase, UpdateTaskUseCase, DeleteTaskUseCase, GetTaskUseCase, CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public TaskApplicationService(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Mono<Task> create(CreateTaskCommand command) {
        return taskRepositoryPort.save(new Task(
                null,
                command.title(),
                command.description(),
                TaskStatus.PENDING,
                command.assignedUserId(),
                LocalDateTime.now()
        ));
    }

    @Override
    public Mono<Task> update(Long taskId, UpdateTaskCommand command) {
        if (taskId == null || taskId <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de tarea invalido"));
        }
        return taskRepositoryPort.findById(taskId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "tarea no encontrada")))
                .flatMap(existing -> taskRepositoryPort.save(new Task(
                        existing.id(),
                        command.title(),
                        command.description(),
                        command.status(),
                        command.assignedUserId(),
                        existing.createdAt()
                )));
    }

    @Override
    public Mono<Void> delete(Long taskId) {
        if (taskId == null || taskId <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de tarea invalido"));
        }
        return taskRepositoryPort.findById(taskId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "tarea no encontrada")))
                .flatMap(task -> taskRepositoryPort.deleteById(taskId));
    }

    @Override
    public Flux<Task> getAll() {
        return taskRepositoryPort.findAll();
    }

    @Override
    public Mono<Task> getById(Long id) {
        return taskRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "tarea no encontrada")));
    }

    @Override
    public Mono<Task> complete(Long taskId, CompleteTaskCommand command) {
        if (taskId == null || taskId <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "id de tarea invalido"));
        }
        if (command == null || command.actorRole() == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "authentication required"));
        }
        return taskRepositoryPort.findById(taskId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "tarea no encontrada")))
                .flatMap(task -> {
                    if (command.actorRole() == Role.USER && (task.assignedUserId() == null || !task.assignedUserId().equals(command.actorUserId()))) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "solo puedes completar tus tareas asignadas"));
                    }

                    if (task.status() == TaskStatus.COMPLETED) {
                        return Mono.just(task);
                    }

                    Task updatedTask = new Task(
                            task.id(),
                            task.title(),
                            task.description(),
                            TaskStatus.COMPLETED,
                            task.assignedUserId(),
                            task.createdAt()
                    );
                    return taskRepositoryPort.save(updatedTask);
                });
    }
}
