package com.example.catalog.mappers;

import com.example.catalog.domain.dto.GroupRequestDTO;
import com.example.catalog.domain.dto.GroupResponseDTO;
import com.example.catalog.domain.entities.Group;

public class GroupMapper {
    public static Group toEntity(GroupRequestDTO dto) {
        Group group = new Group();
        group.setName(dto.getName());
        group.setAdminUser(dto.getAdminUser());
        group.setUsers(dto.getUsers());
        return group;
    }

    public static GroupResponseDTO toDTO(Group group) {
        return new GroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getAdminUser(),
                group.getUsers()
        );
    }
}
