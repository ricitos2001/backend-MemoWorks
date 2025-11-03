package com.example.catalog.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO2 {
    @NotBlank(message = "El título no puede estar vacío")
    private Long id;
    private String title;
    private String description;
    private Date date;
    private Long assigmentfor;
    private Boolean status;
}

