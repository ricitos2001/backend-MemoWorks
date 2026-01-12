package com.example.catalog.web.controllers;

import com.example.catalog.domain.dto.TaskRequestDTO;
import com.example.catalog.domain.dto.TaskResponseDTO;
import com.example.catalog.domain.entities.Task;
import com.example.catalog.domain.entities.User;
import com.example.catalog.services.TaskService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SecurityScheme(
        name = "BearerAuth",
        type=SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"

)
@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> list(@RequestParam(name = "status", required = false) Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.list(pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/myTasks/{email}")
    public ResponseEntity<Page<TaskResponseDTO>> listByUserEmail(@PathVariable(name = "email") String email, Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.listByUserEmail(email, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<TaskResponseDTO> getByTitle(@PathVariable(name = "title") String title) {
        TaskResponseDTO task = taskService.showByTitle(title);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable(name = "id") Long id) {
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

    @PostMapping("/{id}/avatar")
    public ResponseEntity<?> cargarAvatar(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile file) {
        try {
            taskService.guardarAvatar(id, file);
            return ResponseEntity.ok("Avatar actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cargar el avatar: " + e.getMessage());
        }
    }

    @GetMapping("/me/avatar")
    public ResponseEntity<Resource> obtenerAvatarUsuarioLogueado() {
        Resource avatar = taskService.obtenerAvatarGenerico(null);
        try {
            Path avatarPath = Paths.get(avatar.getURL().getPath());
            MediaType mediaType = determinarMediaType(avatarPath);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=\"" + avatar.getFilename() + "\"")
                    .contentType(mediaType)
                    .body(avatar);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo de avatar", e);
        }
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<Resource> obtenerAvatar(@PathVariable(name = "id") Long id) {
        Resource avatar = taskService.obtenerAvatarGenerico(id);
        try {
            Path avatarPath = Paths.get(avatar.getURL().getPath());
            MediaType mediaType = determinarMediaType(avatarPath);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=\"" + avatar.getFilename() + "\"")
                    .contentType(mediaType)
                    .body(avatar);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo de avatar", e);
        }
    }

    private MediaType determinarMediaType(Path ficheroPath) {
        try {
            String contentType = Files.probeContentType(ficheroPath);
            if (contentType == null || contentType.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de contenido no puede ser nulo o vacío.");
            }
            return switch (contentType.toLowerCase()) {
                case "image/jpeg" -> MediaType.IMAGE_JPEG;
                case "image/png" -> MediaType.IMAGE_PNG;
                case "image/gif" -> MediaType.IMAGE_GIF;
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de imagen no soportado: " + contentType);
            };
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al determinar el tipo de contenido", e);
        }
    }
}