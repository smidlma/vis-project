package com.smidl.app.backend.service;

import com.smidl.app.backend.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
