package com.example.task_tracker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testTaskConstructorAndGetters() {
        Task task = new Task("Test Task", null);
        assertEquals("Test Task", task.getDescription());
        assertNull(task.getUser());
    }

    @Test
    void testSetDescription() {
        Task task = new Task();
        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription());
    }

    @Test
    void testSetUser() {
        Task task = new Task();
        User user = new User("Alice");
        task.setUser(user);
        assertSame(user, task.getUser());
    }
}
