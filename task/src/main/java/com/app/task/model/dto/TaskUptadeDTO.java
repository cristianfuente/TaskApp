package com.app.task.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskUptadeDTO {


    private String title;

    private String description;

    private Boolean complete;

    public TaskUptadeDTO(){}

    public TaskUptadeDTO(String title,String description, Boolean complete){
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
