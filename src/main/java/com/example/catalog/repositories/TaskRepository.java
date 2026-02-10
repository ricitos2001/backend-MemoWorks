package com.example.catalog.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.catalog.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task getTaskById(Long id);

    Task getTaskByTitle(String title);

    boolean existsByTitle(String title);

    Page<Task> findByAssigmentForEmail(String assigmentFor, Pageable pageable);

    Page<Task> findByStatus(Boolean status, Pageable pageable);
}