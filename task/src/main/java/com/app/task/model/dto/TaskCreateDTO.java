package com.app.task.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskCreateDTO {

    @NotBlank
    private String title;

    @NotNull
    private Boolean complete;

    private LocalDate create_at;

    public TaskCreateDTO(){}

    public TaskCreateDTO(String title, Boolean complete, LocalDate create_at){
        this.title = title;
        this.complete = complete;
        this.create_at = create_at;
    }

    // Setters
    public void setTitle(String title){this.title = title;}
    public void setComplete(Boolean complete){this.complete = complete;}
    public void setCreate_at(LocalDate create_at){this.create_at = create_at;}

    // Getters
    public String getTitle(){return this.title;}
    public Boolean getComplete(){return this.complete;}
    public LocalDate getCreate_at(){return this.create_at;}
}
