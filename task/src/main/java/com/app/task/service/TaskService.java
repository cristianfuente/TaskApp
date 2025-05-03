package com.app.task.service;

import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.entity.Task;
import com.app.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // Convierte una Entidad tarea a un DTO TaskCreate
    private TaskCreateDTO convertTaskCreateDTO(Task task){
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle(task.getTitle());
        taskCreateDTO.setComplete(task.getComplete());
        taskCreateDTO.setComplete(task.getComplete());
        return taskCreateDTO;
    }

    // Convierte una DTO TaskAdd a una Entidad Task
    private Task convertTask(TaskAddDTO taskAddDTO){
        Task task = new Task();
        task.setTitle(taskAddDTO.getTitle());
        task.setDescription(taskAddDTO.getDescription());
        task.setComplete(taskAddDTO.getComplete());
        task.setCreate_at(LocalDate.now());
        return task;
    }

    // Agrega una nueva Task
    public TaskCreateDTO addTask(TaskAddDTO taskAddDTO){
        Task task = convertTask(taskAddDTO);
        return convertTaskCreateDTO(this.taskRepository.save(task));
    }

    //

}
