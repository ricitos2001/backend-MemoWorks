package com.example.catalog.web.exceptions;

import java.time.LocalDate;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String title) {
        super("task not found: " + title);
    }
    public TaskNotFoundException(Long id) {
        super("task not found: " + id);
    }

    public TaskNotFoundException(LocalDate date) {
        super("task not found: " + date);
    }


}
