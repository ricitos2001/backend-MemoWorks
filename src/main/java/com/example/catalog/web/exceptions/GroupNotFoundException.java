package com.example.catalog.web.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String name) {
        super("group not found: " + name);
    }
    public GroupNotFoundException(Long id) {
        super("group not found: " + id);
    }

}
