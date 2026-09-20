package com.prelim.taskmanager.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prelim.taskmanager.model.Task;

@Repository
public class TaskRepository {
    private List<Task> taskList = new ArrayList<>();

    public List<Task> findAll() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void saveTask(Task task) {
        taskList.add(task);
    }

    public Task findById(Long id) {
        return taskList.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

}
