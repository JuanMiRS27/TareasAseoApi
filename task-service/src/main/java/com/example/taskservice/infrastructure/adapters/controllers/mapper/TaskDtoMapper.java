package com.example.taskservice.infrastructure.adapters.controllers.mapper;

import com.example.taskservice.application.usecases.command.CreateTaskCommand;
import com.example.taskservice.application.usecases.command.UpdateTaskCommand;
import com.example.taskservice.domain.entities.Task;
import com.example.taskservice.infrastructure.adapters.controllers.dto.CreateTaskRequest;
import com.example.taskservice.infrastructure.adapters.controllers.dto.TaskResponse;
import com.example.taskservice.infrastructure.adapters.controllers.dto.UpdateTaskRequest;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {

    public CreateTaskCommand toCommand(CreateTaskRequest request) {
        return new CreateTaskCommand(request.title(), request.description(), request.assignedUserId());
    }

    public UpdateTaskCommand toCommand(UpdateTaskRequest request) {
        return new UpdateTaskCommand(request.title(), request.description(), request.assignedUserId(), request.status());
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.id(),
                task.title(),
                task.description(),
                task.status(),
                task.assignedUserId(),
                task.createdAt()
        );
    }
}
