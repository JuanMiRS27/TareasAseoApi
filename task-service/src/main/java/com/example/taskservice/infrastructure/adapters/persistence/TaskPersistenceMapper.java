package com.example.taskservice.infrastructure.adapters.persistence;

import com.example.taskservice.domain.entities.Task;
import com.example.taskservice.domain.entities.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaskPersistenceMapper {

    public Task toDomain(TaskR2dbcEntity entity) {
        TaskStatus parsedStatus = parseStatus(entity.getStatus());
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                parsedStatus,
                entity.getAssignedUserId(),
                entity.getCreatedAt()
        );
    }

    public TaskR2dbcEntity toEntity(Task task) {
        return new TaskR2dbcEntity(
                task.id(),
                task.title(),
                task.description(),
                task.status().name(),
                task.assignedUserId(),
                task.createdAt()
        );
    }

    private TaskStatus parseStatus(String rawStatus) {
        if (rawStatus == null || rawStatus.isBlank()) {
            return TaskStatus.PENDING;
        }
        try {
            return TaskStatus.valueOf(rawStatus.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            return TaskStatus.PENDING;
        }
    }
}
