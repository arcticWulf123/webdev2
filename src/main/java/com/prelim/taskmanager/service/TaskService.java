package com.prelim.taskmanager.service;

import org.springframework.stereotype.Service;

import com.prelim.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    
}
