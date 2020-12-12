package com.smidl.app.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String role = "USER";

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = false)
    private Boolean locked =  false;

    @OneToMany(mappedBy = "manager")
    private List<Project> myProjects;

    @ManyToMany
    private List<Project> projects;

    @OneToMany(mappedBy = "creator")
    private List<Task> createTasks;

    @OneToMany(mappedBy = "solver")
    private List<Task> solveTasks;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

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

  

}
