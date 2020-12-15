package com.smidl.app.backend.service;

import java.util.List;
import java.util.Set;

import com.smidl.app.backend.model.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public long count() {
        return taskRepository.count();
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public void save(Task task) {
        if (task == null) {
            return;
        }
        taskRepository.save(task);
    }

}
