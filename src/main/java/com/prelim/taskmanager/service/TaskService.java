package com.prelim.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prelim.taskmanager.model.Task;
import com.prelim.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> returnAllTasks() {
        return taskRepository.findAll();
    }

    public Task findById (Long id) {
        return taskRepository.findById(id);
    }

    public void createTask (Task task) {
        taskRepository.saveTask(task);
    }
}
