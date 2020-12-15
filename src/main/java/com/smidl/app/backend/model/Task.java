package com.smidl.app.backend.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Task extends AbstractEntity {

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Priority priority;

    // @Column(nullable = false)
    // private Long difficulty;

    @Column(nullable = false)
    private double price;

    @Column(nullable = true)
    private double hours;

    @Column(nullable = false)
    private LocalDate createDate = LocalDate.now();

    @Column(nullable = true)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDate finishDate;

    @Column(nullable = false)
    private LocalDate deadlineDate;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User solver;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private TaskState state;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

    public Task() {
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // public Long getDifficulty() {
    //     return difficulty;
    // }

    // public void setDifficulty(Long difficulty) {
    //     this.difficulty = difficulty;
    // }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
    
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getSolver() {
        return solver;
    }

    public void setSolver(User solver) {
        this.solver = solver;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

}
