package com.smidl.app.service;

import com.smidl.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    
}
