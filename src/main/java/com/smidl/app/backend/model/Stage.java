package com.smidl.app.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Stage extends AbstractEntity {
    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "stage")
    private List<Task> tasks;

    public Stage() {

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
