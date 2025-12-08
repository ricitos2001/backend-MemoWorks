package com.example.catalog.web.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String title) {
        super("task not found: " + title);
    }
    public TaskNotFoundException(Long id) {
        super("task not found: " + id);
    }

}
