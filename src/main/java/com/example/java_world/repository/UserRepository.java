package com.example.java_world.repository;

import com.example.java_world.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = "tasks")
    List<User> findAllWithTasksBy();
}
