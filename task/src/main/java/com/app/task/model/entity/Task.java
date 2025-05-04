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

    private LocalDate create_at;

    public Task(){}

    public Task(String title,String description, Boolean complete, LocalDate create_at){
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.create_at = create_at;
    }

    // Setters
    public void setId(Long id){this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setComplete(Boolean complete){this.complete = complete;}
    public void setCreate_at(LocalDate create_at){this.create_at = create_at;}

    // Getters
    public Long getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public Boolean getComplete(){return this.complete;}
    public LocalDate getCreate_at(){return this.create_at;}
}
