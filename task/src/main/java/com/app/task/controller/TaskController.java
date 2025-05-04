package com.app.task.controller;

import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.dto.TaskResponseDTO;
import com.app.task.model.dto.TaskUptadeDTO;
import com.app.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // Agregar Tarea
    @PostMapping("/add")
    public ResponseEntity<TaskCreateDTO> setTask(@RequestBody TaskAddDTO taskAddDTO){
        TaskCreateDTO taskCreateDTO = this.taskService.addTask(taskAddDTO);
        return new ResponseEntity<>(taskCreateDTO, HttpStatus.CREATED);
    }

    // Obtener todas las Tareas
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDTO>> getTaskAll(){
        List<TaskResponseDTO> tasks = this.taskService.getAllTask();
        return tasks.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // Obterner Tarea por id
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id){
        Optional<TaskResponseDTO> taskResponseOpt = this.taskService.getTask(id);
        return taskResponseOpt.map(taskResponseDTO -> new ResponseEntity<>(taskResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskCreateDTO> deleteTaks(@PathVariable Long id){
        Optional<TaskCreateDTO> taskCreateOpt = this.taskService.deleteTask(id);
        return taskCreateOpt.map(taskCreateDTO -> new ResponseEntity<>(taskCreateDTO, HttpStatus.NO_CONTENT))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar Tarea por Id
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskUptadeDTO taskUptadeDTO){
        Optional<TaskResponseDTO> taskResponseOpt = this.taskService.updateTask(id,taskUptadeDTO);
        return taskResponseOpt.map(taskResponseDTO -> new ResponseEntity<>(taskResponseDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
