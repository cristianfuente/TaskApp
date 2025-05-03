package com.app.task.service;

import com.app.task.model.dto.TaskResponseDTO;
import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.entity.Task;
import com.app.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // Convierte una Entidad tarea a un DTO TaskCreate
    private TaskResponseDTO convertTaskResponseDTO(Task task){
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setComplete(task.getComplete());
        taskResponseDTO.setCreate_at(task.getCreate_at());
        return taskResponseDTO;
    }


    // Convierte una Entidad tarea a un DTO TaskCreate
    private TaskCreateDTO convertTaskCreateDTO(Task task){
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle(task.getTitle());
        taskCreateDTO.setComplete(task.getComplete());
        taskCreateDTO.setCreate_at(task.getCreate_at());
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

    public Optional<Task> searchTaks(Long id){
        return this.taskRepository.findById(id);
    }

    // Listar un Task
    public Optional<TaskResponseDTO> getTask(Long id){
        Optional<Task>  taskOpt = searchTaks(id);
        if(taskOpt.isEmpty()){
            return  Optional.empty();
        }
        TaskResponseDTO taskResponseDTO = convertTaskResponseDTO(taskOpt.get());
        return Optional.of(taskResponseDTO);
    }

}
