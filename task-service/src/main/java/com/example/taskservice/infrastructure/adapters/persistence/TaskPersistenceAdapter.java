package com.example.taskservice.infrastructure.adapters.persistence;

import com.example.taskservice.domain.entities.Task;
import com.example.taskservice.domain.ports.TaskRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TaskPersistenceAdapter implements TaskRepositoryPort {

    private final ReactiveTaskRepository reactiveTaskRepository;
    private final TaskPersistenceMapper mapper;

    public TaskPersistenceAdapter(ReactiveTaskRepository reactiveTaskRepository, TaskPersistenceMapper mapper) {
        this.reactiveTaskRepository = reactiveTaskRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Task> save(Task task) {
        return reactiveTaskRepository.save(mapper.toEntity(task)).map(mapper::toDomain);
    }

    @Override
    public Mono<Task> findById(Long id) {
        return reactiveTaskRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<Task> findAll() {
        return reactiveTaskRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return reactiveTaskRepository.deleteById(id);
    }
}
