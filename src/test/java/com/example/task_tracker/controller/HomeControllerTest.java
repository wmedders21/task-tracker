package com.example.task_tracker.controller;

import com.example.task_tracker.model.User;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.repository.UserRepository;
import com.example.task_tracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testHomePageLoads() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Task Tracker")));
    }

    @Test
    void testAddUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Test User"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("User added successfully")));
    }

    @Test
    void testAssignTask() throws Exception {
        User user = new User("Task Assignee");
        userRepository.save(user);
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "Test Task")
                .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Task assigned to Task Assignee")));
    }

    @Test
    void testShowUser() throws Exception {
        User user = new User("Show User");
        userRepository.save(user);
        mockMvc.perform(get("/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Show User")));
    }

    @Test
    void testCompleteTask() throws Exception {
        User user = new User("Complete User");
        userRepository.save(user);
        Task task = new Task("Complete Me", user);
        taskRepository.save(task);
        mockMvc.perform(post("/tasks/" + task.getId() + "/complete"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void testReadmePage() throws Exception {
        mockMvc.perform(get("/readme"))
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("TaskTracker")));
    }
}
