package com.example.catalog.repositories;

import com.example.catalog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);

    boolean existsByUsername(String username);

    User getUserById(Long id);
}
