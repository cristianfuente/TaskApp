package com.app.task.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TaskAddDTO {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Boolean complete;

    public TaskAddDTO(){}

    public TaskAddDTO(String title,String description, Boolean complete){
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    // Setters
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setComplete(Boolean complete){this.complete = complete;}

    // Getters
    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public Boolean getComplete(){return this.complete;}
}
