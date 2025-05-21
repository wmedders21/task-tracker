package com.example.task_tracker.repository;

import com.example.task_tracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFind() {
        User user = new User("Repo User");
        userRepository.save(user);
        List<User> found = userRepository.findByNameContainingIgnoreCase("repo");
        assertFalse(found.isEmpty());
        assertEquals("Repo User", found.get(0).getName());
    }

    @Test
    void testFindAllWithTasksBy() {
        User user = new User("Task User");
        userRepository.save(user);
        List<User> users = userRepository.findAllWithTasksBy();
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Task User")));
    }
}
