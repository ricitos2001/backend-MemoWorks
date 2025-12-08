package com.example.catalog.domain.dto;

import com.example.catalog.domain.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDTO {
    @Size(max=25, message = "el nombre del grupo no puede tener mas de 25 caracteres")
    private String name;
    @NotNull(message = "es obligatorio asignar un administrador")
    private User adminUser;
    private List<User> users;
}
