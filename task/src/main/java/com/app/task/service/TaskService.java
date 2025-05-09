package com.app.task.service;

import com.app.task.model.dto.TaskResponseDTO;
import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.dto.TaskUptadeDTO;
import com.app.task.model.entity.Task;
import com.app.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
        taskResponseDTO.setCreate_at(task.getCreateAt());
        return taskResponseDTO;
    }


    // Convierte una Entidad tarea a un DTO TaskCreate
    private TaskCreateDTO convertTaskCreateDTO(Task task){
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle(task.getTitle());
        taskCreateDTO.setComplete(task.getComplete());
        taskCreateDTO.setCreate_at(task.getCreateAt());
        return taskCreateDTO;
    }

    // Convierte una DTO TaskAdd a una Entidad Task
    private Task convertTask(TaskAddDTO taskAddDTO){
        Task task = new Task();
        task.setTitle(taskAddDTO.getTitle());
        task.setDescription(taskAddDTO.getDescription());
        task.setComplete(taskAddDTO.getComplete());
        task.setCreateAt(LocalDate.now());
        return task;
    }

    // Agrega una nueva Task
    @Transactional
    public TaskCreateDTO addTask(TaskAddDTO taskAddDTO){
        Task task = convertTask(taskAddDTO);
        return convertTaskCreateDTO(this.taskRepository.save(task));
    }

    public Optional<Task> searchTask(Long id){
        return this.taskRepository.findById(id);
    }

    // Listar un Task
    public Optional<TaskResponseDTO> getTask(Long id){
        Optional<Task>  taskOpt = searchTask(id);
        if(taskOpt.isEmpty()){
            return  Optional.empty();
        }
        TaskResponseDTO taskResponseDTO = convertTaskResponseDTO(taskOpt.get());
        return Optional.of(taskResponseDTO);
    }

    // Listar todas las Tasks
    public List<TaskResponseDTO> getAllTask(){
        List<Task> tasks = this.taskRepository.findAll();
        return  tasks.stream()
                    .map(this::convertTaskResponseDTO)
                    .toList();
    }

    // Eliminar un Task
    @Transactional
    public Optional<TaskCreateDTO> deleteTask(Long id){
        Optional<Task> taskOpt = searchTask(id);
        if(taskOpt.isEmpty()){
            return Optional.empty();
        }
        this.taskRepository.delete(taskOpt.get());
        return Optional.of(convertTaskCreateDTO(taskOpt.get()));
    }

    private Task changeAtributesTask(TaskUptadeDTO taskUptadeDTO, Task task){
        if(taskUptadeDTO.getTitle() != null && !taskUptadeDTO.getTitle().isEmpty()){
           task.setTitle(taskUptadeDTO.getTitle());
        }
        if(taskUptadeDTO.getDescription() != null && !taskUptadeDTO.getDescription().isEmpty()){
            task.setDescription(taskUptadeDTO.getDescription());
        }
        if(taskUptadeDTO.getComplete() != null ){
            task.setComplete(taskUptadeDTO.getComplete());
        }
        task.setCreateAt(LocalDate.now());
        return task;
    }

    // Actualizar un Task
    @Transactional
    public Optional<TaskResponseDTO> updateTask(Long id, TaskUptadeDTO taskUptadeDTO){
        Optional<Task> taskOpt = searchTask(id);
        if(taskOpt.isEmpty()){
            return Optional.empty();
        }
        Task task = this.taskRepository.save(changeAtributesTask(taskUptadeDTO,taskOpt.get()));
        return Optional.of(convertTaskResponseDTO(task));
    }

}
