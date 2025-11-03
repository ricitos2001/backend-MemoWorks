package com.example.catalog.domain.dto;

import java.util.Date;

public record CreateTaskDTO(Long id, String title, String description, Date date, Long assigmentFor, Boolean status) {}
