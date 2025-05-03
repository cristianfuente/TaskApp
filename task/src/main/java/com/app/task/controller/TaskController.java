package com.app.task.controller;

import com.app.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // Agregar Tarea
    @PostMapping("/add")
    public ResponseEntity<> setTask(@RequestBody ){

    }

    // Obtener todas las Tareas
    @GetMapping("/all")
    public ResponseEntity<> getTaskAll(){
    }

    // Obterner Tarea por id
    @GetMapping("/{id}")
    public ResponseEntity<> getTask(@PathVariable Long id){

    }

    // Actualizar Tarea por Id
    @PutMapping("{id}")
    public ResponseEntity<> updateTask(@PathVariable Long id){

    }

    @DeleteMapping("/{id}"")
    public ResponseEntity<> deleteTaks(@PathVariable Long id){

    }


}
