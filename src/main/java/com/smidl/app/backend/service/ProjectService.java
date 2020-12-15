package com.smidl.app.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.smidl.app.backend.model.Project;
import com.smidl.app.backend.model.User;
import com.smidl.app.backend.model.UserProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private UserProjectRepository userProjectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserProjectRepository userProjectRepository) {
        this.projectRepository = projectRepository;
        this.userProjectRepository = userProjectRepository;
    }

    public void addTeamMember(Set<User> teamMembers, Project p) {
        // p.getUsers().forEach(up -> {
        //     teamMembers.remove(up.getUser());
        // });

        teamMembers.forEach(u -> {
            UserProject up = new UserProject();
            up.setUser(u);
            up.setProject(p);
            userProjectRepository.save(up);
        });
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).get();
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public long count() {
        return projectRepository.count();
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Project save(Project project) {
        if (project == null) {
            return null;
        }
        return projectRepository.save(project);
    }

}
