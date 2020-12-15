package com.smidl.app.backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project extends AbstractEntity {

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private String description;

    private Date createdAt;

    @ManyToOne
    private User manager;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<UserProject> users = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Task> tasks; 

    public Project() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<UserProject> getUsers() {
        return users;
    }

    public void setUsers(Set<UserProject> users) {
        this.users = users;
    }

  

  


}
