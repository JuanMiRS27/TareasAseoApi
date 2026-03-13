package com.example.taskservice.infrastructure.adapters.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveTaskRepository extends ReactiveCrudRepository<TaskR2dbcEntity, Long> {
}
