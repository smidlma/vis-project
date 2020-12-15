package com.smidl.app.backend.service;

import com.smidl.app.backend.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
