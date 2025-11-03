package com.example.catalog.services;

import java.util.List;

import com.example.catalog.domain.dto.CreateTaskDTO;
import com.example.catalog.domain.entities.Task;
import com.example.catalog.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) { this.taskRepository = taskRepository; }

    public List<Task> list(Boolean status) {
        return (status == null) ? taskRepository.findAll() : taskRepository.findByStatus(status);
    }

    public Task showTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public Task create(CreateTaskDTO dto) {
        Task newTask = Task.builder().title(dto.title()).description(dto.description()).date(dto.date()).status(false).build();
        return taskRepository.save(newTask);
    }

    public Task toggle(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setDate(task.getDate());
        task.setStatus(!task.getStatus());
        return task;
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) throw new IllegalArgumentException("Task not found");
        taskRepository.deleteById(id);
    }
}