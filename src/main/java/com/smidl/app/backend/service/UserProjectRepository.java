package com.smidl.app.backend.service;

import com.smidl.app.backend.model.UserProject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    
}
