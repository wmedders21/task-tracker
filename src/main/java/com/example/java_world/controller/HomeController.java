package com.example.java_world.controller;

import com.example.java_world.model.User;
import com.example.java_world.repository.UserRepository;
import com.example.java_world.repository.TaskRepository;
import com.example.java_world.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public HomeController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        int page = 0;
        int pageSize = 5;
        List<User> users = userRepository.findAllWithTasksBy();
        users.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        });
        int totalUsers = users.size();
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalUsers);
        List<User> pagedUsers = users.subList(fromIndex, toIndex);
        model.addAttribute("users", pagedUsers);
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalUsers / pageSize));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalUsers", totalUsers);
        List<User> allUsers = userRepository.findAll();
        allUsers.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        }); // Sort by first name
        model.addAttribute("allUsers", allUsers);
        return "home";
    }

    @GetMapping("/users/page")
    public String paginate(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 5;
        List<User> users = userRepository.findAllWithTasksBy();
        users.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        });
        int totalUsers = users.size();
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalUsers);
        List<User> pagedUsers = users.subList(fromIndex, toIndex);
        model.addAttribute("users", pagedUsers);
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalUsers / pageSize));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalUsers", totalUsers);
        List<User> allUsers = userRepository.findAll();
        allUsers.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        }); // Sort by first name
        model.addAttribute("allUsers", allUsers);
        return "home :: #user-list";
    }

    @PostMapping(value = "/users", produces = "text/vnd.turbo-stream.html")
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestParam String name, Model model) {
        try {
            userRepository.save(new User(name));
            int page = 0;
            int pageSize = 5;
            // Always sort users by first name for the table
            List<User> users = userRepository.findAllWithTasksBy();
            users.sort((a, b) -> {
                String aFirst = a.getName().split(" ")[0];
                String bFirst = b.getName().split(" ")[0];
                return aFirst.compareToIgnoreCase(bFirst);
            });
            int totalUsers = users.size();
            int fromIndex = page * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalUsers);
            List<User> pagedUsers = users.subList(fromIndex, toIndex);
            model.addAttribute("users", pagedUsers);
            model.addAttribute("pageNumber", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalUsers / pageSize));
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalUsers", totalUsers);
            List<User> allUsers = userRepository.findAll();
            allUsers.sort((a, b) -> {
                String aFirst = a.getName().split(" ")[0];
                String bFirst = b.getName().split(" ")[0];
                return aFirst.compareToIgnoreCase(bFirst);
            });
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("successMessage", "User added successfully!");
        } catch (Exception e) {
            int page = 0;
            int pageSize = 5;
            List<User> users = userRepository.findAllWithTasksBy();
            users.sort((a, b) -> {
                String aFirst = a.getName().split(" ")[0];
                String bFirst = b.getName().split(" ")[0];
                return aFirst.compareToIgnoreCase(bFirst);
            });
            int totalUsers = users.size();
            int fromIndex = page * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalUsers);
            List<User> pagedUsers = users.subList(fromIndex, toIndex);
            model.addAttribute("users", pagedUsers);
            model.addAttribute("pageNumber", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalUsers / pageSize));
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalUsers", totalUsers);
            List<User> allUsers = userRepository.findAll();
            allUsers.sort((a, b) -> {
                String aFirst = a.getName().split(" ")[0];
                String bFirst = b.getName().split(" ")[0];
                return aFirst.compareToIgnoreCase(bFirst);
            }); // Sort by first name
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("errorMessage", "Failed to add user: " + e.getMessage());
        }
        Context ctx = new Context(null, model.asMap());
        String turboStream = templateEngine.process("addUserSuccess.stream", ctx);
        return ResponseEntity.ok()
            .header("Content-Type", "text/vnd.turbo-stream.html; charset=UTF-8")
            .body(turboStream);
    }

    @GetMapping("/users/filter")
    public String filter(@RequestParam String q, Model model) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(q);
        model.addAttribute("users", users);
        return "home :: #user-list";
    }

    @PostMapping(value = "/tasks", produces = "text/vnd.turbo-stream.html")
    @ResponseBody
    public ResponseEntity<String> addTask(@RequestParam String description, @RequestParam Long userId, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            taskRepository.save(new Task(description, user));
            model.addAttribute("successMessage", "Task assigned to " + user.getName() + "!");
        } else {
            model.addAttribute("errorMessage", "User not found.");
        }
        int page = 0;
        int pageSize = 5;
        List<User> users = userRepository.findAllWithTasksBy();
        users.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        });
        int totalUsers = users.size();
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalUsers);
        List<User> pagedUsers = users.subList(fromIndex, toIndex);
        model.addAttribute("users", pagedUsers);
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalUsers / pageSize));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalUsers", totalUsers);
        List<User> allUsers = userRepository.findAll();
        allUsers.sort((a, b) -> {
            String aFirst = a.getName().split(" ")[0];
            String bFirst = b.getName().split(" ")[0];
            return aFirst.compareToIgnoreCase(bFirst);
        });
        model.addAttribute("allUsers", allUsers);
        // Remove forced addition of user to pagedUsers; just use correct sort order
        Context ctx = new Context(null, model.asMap());
        String turboStream = templateEngine.process("addTaskSuccess.stream", ctx);
        return ResponseEntity.ok()
            .header("Content-Type", "text/vnd.turbo-stream.html; charset=UTF-8")
            .body(turboStream);
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        User user = userRepository.findAllWithTasksBy().stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found.");
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("tasks", user.getTasks());
        return "user_show";
    }

    @PostMapping("/tasks/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId, Model model) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            Long userId = task.getUser().getId();
            taskRepository.delete(task);
            return "redirect:/users/" + userId;
        } else {
            model.addAttribute("errorMessage", "Task not found.");
            return "redirect:/";
        }
    }

    @GetMapping("/readme")
    public String readme(Model model) {
        try {
            String content = Files.readString(Paths.get("README.md"));
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);
            model.addAttribute("readmeContent", html);
        } catch (Exception e) {
            model.addAttribute("readmeContent", "<p>Could not load README.md</p>");
        }
        return "readme";
    }
}
