package com.example.taskservice.infrastructure.adapters.controllers;

import com.example.taskservice.application.usecases.CompleteTaskUseCase;
import com.example.taskservice.application.usecases.CreateTaskUseCase;
import com.example.taskservice.application.usecases.DeleteTaskUseCase;
import com.example.taskservice.application.usecases.GetTaskUseCase;
import com.example.taskservice.application.usecases.UpdateTaskUseCase;
import com.example.taskservice.application.usecases.command.CompleteTaskCommand;
import com.example.taskservice.infrastructure.adapters.controllers.dto.CreateTaskRequest;
import com.example.taskservice.infrastructure.adapters.controllers.dto.TaskResponse;
import com.example.taskservice.infrastructure.adapters.controllers.dto.UpdateTaskRequest;
import com.example.taskservice.infrastructure.adapters.controllers.mapper.TaskDtoMapper;
import com.example.taskservice.infrastructure.adapters.security.AuthenticatedUser;
import com.example.taskservice.infrastructure.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final TaskDtoMapper mapper;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            UpdateTaskUseCase updateTaskUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            GetTaskUseCase getTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase,
            TaskDtoMapper mapper
    ) {
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getTaskUseCase = getTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<TaskResponse>>> create(@Valid @RequestBody CreateTaskRequest request) {
        return createTaskUseCase.create(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<TaskResponse>>> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateTaskRequest request) {
        return updateTaskUseCase.update(id, mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(@PathVariable("id") Long id) {
        return deleteTaskUseCase.delete(id)
                .thenReturn(ResponseEntity.ok(ApiResponse.success(null)));
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<java.util.List<TaskResponse>>>> getAll() {
        return getTaskUseCase.getAll()
                .map(mapper::toResponse)
                .collectList()
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<TaskResponse>>> getById(@PathVariable("id") Long id) {
        return getTaskUseCase.getById(id)
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/complete")
    public Mono<ResponseEntity<ApiResponse<TaskResponse>>> complete(@PathVariable("id") Long id) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .filter(authentication -> authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser)
                .map(authentication -> (AuthenticatedUser) authentication.getPrincipal())
                .switchIfEmpty(Mono.error(new ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED, "authentication required")))
                .flatMap(principal -> completeTaskUseCase.complete(id, new CompleteTaskCommand(principal.userId(), principal.role())))
                .map(mapper::toResponse)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok);
    }
}
