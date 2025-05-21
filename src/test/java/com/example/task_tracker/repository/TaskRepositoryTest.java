package com.example.task_tracker.repository;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFind() {
        User user = new User("Task Owner");
        userRepository.save(user);
        Task task = new Task("Do something", user);
        taskRepository.save(task);
        List<Task> tasks = taskRepository.findAll();
        assertFalse(tasks.isEmpty());
        assertEquals("Do something", tasks.get(0).getDescription());
        assertEquals(user.getName(), tasks.get(0).getUser().getName());
    }
}
