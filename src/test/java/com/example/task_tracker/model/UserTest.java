package com.example.task_tracker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class UserTest {
    @Test
    void testUserConstructorAndGetters() {
        User user = new User("John Doe");
        assertEquals("John Doe", user.getName());
        assertNotNull(user.getTasks());
    }

    @Test
    void testSetName() {
        User user = new User();
        user.setName("Jane Smith");
        assertEquals("Jane Smith", user.getName());
    }

    @Test
    void testSetTasks() {
        User user = new User();
        Task task = new Task();
        user.setTasks(List.of(task));
        assertEquals(1, user.getTasks().size());
        assertSame(task, user.getTasks().get(0));
    }
}
