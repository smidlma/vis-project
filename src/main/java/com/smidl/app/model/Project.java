package com.smidl.app.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

    @OneToMany(mappedBy = "project")
    private List<Stage> stages;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

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

}