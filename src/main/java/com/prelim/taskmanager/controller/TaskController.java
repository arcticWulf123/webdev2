package com.prelim.taskmanager.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prelim.taskmanager.TaskNotFoundException;
import com.prelim.taskmanager.model.Task;
import com.prelim.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
    
    

}
