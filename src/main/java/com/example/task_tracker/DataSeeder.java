package com.example.task_tracker;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.User;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeeder {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public DataSeeder(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
            if (userRepository.count() == 0) {
                User[] users = new User[] {
                    new User("Alice Smith"), new User("Bob Johnson"), new User("Charlie Williams"), new User("Diana Brown"), new User("Ethan Jones"),
                    new User("Fiona Miller"), new User("George Davis"), new User("Hannah Garcia"), new User("Ian Martinez"), new User("Julia Rodriguez"),
                    new User("Kevin Wilson"), new User("Laura Anderson"), new User("Michael Thomas"), new User("Nina Taylor"), new User("Oscar Moore"),
                    new User("Paula Jackson"), new User("Quentin Martin"), new User("Rachel Lee"), new User("Samuel Perez"), new User("Tina White"),
                    new User("Uma Harris"), new User("Victor Clark"), new User("Wendy Lewis"), new User("Xander Walker"), new User("Yara Hall"),
                    new User("Zane Allen")
                };
                userRepository.saveAll(Arrays.asList(users));
                // Seed tasks for a few users
                taskRepository.saveAll(Arrays.asList(
                    new Task("Buy groceries", users[0]),
                    new Task("Finish project", users[0]),
                    new Task("Read a book", users[1]),
                    new Task("Go jogging", users[2]),
                    new Task("Call mom", users[3]),
                    new Task("Pay bills", users[4])
                ));
            }
        };
    }
}
