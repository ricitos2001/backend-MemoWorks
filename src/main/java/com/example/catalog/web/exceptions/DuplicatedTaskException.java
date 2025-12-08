package com.example.catalog.web.exceptions;

public class DuplicatedTaskException extends RuntimeException {
    public DuplicatedTaskException(String title) {
        super("the task already exists: " + title);
    }
}
