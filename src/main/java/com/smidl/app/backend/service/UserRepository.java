package com.smidl.app.backend.service;



import com.smidl.app.backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByEmailIgnoreCase(String email);
}
