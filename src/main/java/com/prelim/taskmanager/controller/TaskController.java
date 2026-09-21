package com.prelim.taskmanager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prelim.taskmanager.TaskNotFoundException;
import com.prelim.taskmanager.model.Task;
import com.prelim.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.returnAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task was not found...");
        }
        model.addAttribute("task", task);
        return "task-detail";
    }

    @PostMapping()
    public String postTask(@Valid @ModelAttribute Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "task-form";
        }
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound (TaskNotFoundException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "not-found";
    }

}
