package com.example.catalog.services;

import com.example.catalog.domain.dto.CreateTaskDTO;
import com.example.catalog.domain.dto.CreateUserDTO;
import com.example.catalog.domain.entities.Task;
import com.example.catalog.domain.entities.User;
import com.example.catalog.repositories.TaskRepository;
import com.example.catalog.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User showUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User create(CreateUserDTO dto) {
        User newUser = User.builder().name(dto.name()).username(dto.username()).phoneNumber(dto.phoneNumber()).email(dto.email()).password(dto.password()).build();
        return userRepository.save(newUser);
    }

    public User toggle(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        return user;
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) throw new IllegalArgumentException("User not found");
        userRepository.deleteById(id);
    }
}
