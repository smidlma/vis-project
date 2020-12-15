package com.smidl.app.backend.service;

import java.util.List;
import java.util.Set;

import com.smidl.app.backend.model.UserProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProjectService {
    private UserProjectRepository userProjectRepository;

    @Autowired
    public UserProjectService(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }

    public UserProject findById(Long id) {
        return userProjectRepository.findById(id).get();

    }

    public List<UserProject> findAll() {
        return userProjectRepository.findAll();
    }

    public long count() {
        return userProjectRepository.count();
    }

    public void delete(UserProject userProject) {
        userProjectRepository.delete(userProject);
    }

    public UserProject save(UserProject userProject) {
        if (userProject == null) {
            return null;
        }
        return userProjectRepository.save(userProject);
    }
}
