package com.app.task.model.entity;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Boolean complete;

    @Column(name = "create_at")
    private LocalDate createAt;

    public Task(){}

    public Task(String title,String description, Boolean complete, LocalDate createAt){
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.createAt = createAt;
    }

    // Setters
    public void setId(Long id){this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setComplete(Boolean complete){this.complete = complete;}
    public void setCreateAt(LocalDate createAt){this.createAt = createAt;}

    // Getters
    public Long getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public Boolean getComplete(){return this.complete;}
    public LocalDate getCreateAt(){return this.createAt;}
}
