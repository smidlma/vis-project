package com.smidl.app.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String role;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = false)
    private Boolean locked = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserProject> projects = new HashSet<>();

    @OneToMany(mappedBy = "manager",  fetch = FetchType.EAGER)
    private Set<Project> myProjects = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    private Set<Task> createTasks = new HashSet<>();

    @OneToMany(mappedBy = "solver", fetch = FetchType.EAGER)
    private Set<Task> solveTasks = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Set<Task> getCreateTasks() {
        return createTasks;
    }

    public void setCreateTasks(Set<Task> createTasks) {
        this.createTasks = createTasks;
    }

    public Set<Task> getSolveTasks() {
        return solveTasks;
    }

    public void setSolveTasks(Set<Task> solveTasks) {
        this.solveTasks = solveTasks;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserProject> getProjects() {
        return projects;
    }

    public void setProjects(Set<UserProject> projects) {
        this.projects = projects;
    }

    public Set<Project> getMyProjects() {
        return myProjects;
    }

    public void setMyProjects(Set<Project> myProjects) {
        this.myProjects = myProjects;
    }

}
