package com.example.catalog.web.exceptions;

public class DuplicatedGroupException extends RuntimeException {
    public DuplicatedGroupException(String name) {
        super("the group already exists: " + name);
    }
}
