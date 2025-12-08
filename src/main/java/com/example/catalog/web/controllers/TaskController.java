package com.example.catalog.web.controllers;

import com.example.catalog.domain.dto.TaskRequestDTO;
import com.example.catalog.domain.dto.TaskResponseDTO;
import com.example.catalog.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> list(@RequestParam(name = "status", required = false) Boolean status, Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.list(pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<TaskResponseDTO> getByTitle(@PathVariable(name = "title") String title) {
        TaskResponseDTO task = taskService.showByTitle(title);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskResponseDTO> bookById(@PathVariable(name = "id") Long id) {
        TaskResponseDTO task = taskService.showById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO dto) {
        TaskResponseDTO saved = taskService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable(name = "id") Long id, @RequestBody @Valid TaskRequestDTO dto) {
        TaskResponseDTO toggled = taskService.update(id, dto);
        return ResponseEntity.ok(toggled);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}