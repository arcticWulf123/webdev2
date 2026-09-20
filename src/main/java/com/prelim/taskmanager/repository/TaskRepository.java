package com.prelim.taskmanager.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prelim.taskmanager.model.Task;

@Repository
public class TaskRepository {
    private List<Task> taskList = new ArrayList<>();


    public TaskRepository () {
        taskList.add(new Task(taskList.size() + 1L, "Clean my room", "Room must be swept and mopped", false));
        taskList.add(new Task(taskList.size() + 1L, "Take out the garbage", "Dispose the garbage right outside the house", true));
        taskList.add(new Task(taskList.size() + 1L, "Feed the cats", "Feed and clean their litter", true));
        taskList.add(new Task(taskList.size() + 1L, "Take a walk", "Take a short, 15 minute walk outside", false));
    }
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
