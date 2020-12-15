package com.smidl.app.backend.service;

import java.util.List;

import com.smidl.app.backend.model.User;
import com.smidl.app.security.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer taskCount() {
        User u = this.findByEmail(SecurityUtils.getUsername());
        return u.getSolveTasks().size();
    }

    public Integer projectCount() {
        User u = this.findByEmail(SecurityUtils.getUsername());
        return u.getMyProjects().size();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long count() {
        return userRepository.count();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void save(User user) {
        if (user == null) {
            return;
        }
        userRepository.save(user);
    }
}
