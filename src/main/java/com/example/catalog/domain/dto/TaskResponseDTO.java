package com.example.catalog.domain.dto;

import com.example.catalog.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private User assigmentFor;
    private Boolean status;
}
