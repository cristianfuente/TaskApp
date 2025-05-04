package com.app.task;

import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.dto.TaskResponseDTO;
import com.app.task.model.dto.TaskUptadeDTO;
import com.app.task.model.entity.Task;
import com.app.task.repository.TaskRepository;
import com.app.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // Habilita Mockito
public class TaskServiceTest {

    @Mock // Mockea la dependencia del repositorio
    private TaskRepository taskRepository;

    @InjectMocks  // Crea una instancia del servicio e inyecta los mocks
    private TaskService taskService;

    @Test
    public void saveTaskTest(){
        // 1. Arrange
        String title = "Ingenieria de Software 3";
        String description = "Realizar el taller 2 creacion de un pipelide de despliege continuo";
        Boolean complete = false;
        LocalDate createAt = LocalDate.now();
        TaskAddDTO taskAddDTO = new TaskAddDTO(title,description,complete);

        // Define el comportamiento del Mock: cuando se llame save
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return task; // Devuelve el mismo objeto que se pasa
        });
        // 2. Act
        TaskCreateDTO result = taskService.addTask(taskAddDTO);

        // 3. Assert
        assertNotNull(result);
        assertEquals(title,result.getTitle());
        assertEquals(createAt,result.getCreate_at());
        assertFalse(result.getComplete());

        // Captura el argumento pasado al metodo save
        ArgumentCaptor<Task> taskCaptor = forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());

        // Verifica que el objeto capturado tenga las propiedades esperadas
        Task savedTask = taskCaptor.getValue();
        assertEquals(title, savedTask.getTitle());
        assertEquals(description, savedTask.getDescription());
        assertEquals(complete, savedTask.getComplete());
        assertEquals(createAt, savedTask.getCreateAt());
    }

    @Test
    public void getTaskByIdContentTest(){
        // 1. Arrange
        Long id = 1L;
        String title = "Ingenieria de Software 3";
        String description = "Realizar el taller 2 creacion de un pipelide de despliege continuo";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        Task task = new Task(title,description,complete,create_at);

        // Simula la asignación de un ID autogenerado
        task.setId(1L);

        // Define el comportamiento del Mock para findById()
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // 2. Act
        Optional<TaskResponseDTO> resultOpt = taskService.getTask(id);

        // 3. Assert
        assertTrue(resultOpt.isPresent());
        assertEquals(id,resultOpt.get().getId());
        assertEquals(title, resultOpt.get().getTitle());
        assertEquals(description, resultOpt.get().getDescription());
        assertEquals(complete, resultOpt.get().getComplete());
        assertEquals(create_at, resultOpt.get().getCreate_at());
    }

    @Test
    public void getTaskByIdNotContentTest(){

        // 1. Arrange
        Long id = 99L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // 2. Act
        Optional<TaskResponseDTO> result = taskService.getTask(id);

        // 3. Assert
        assertFalse(result.isPresent());
        verify(taskRepository).findById(id);
    }

    @Test
    public void deleteTaskByIdContentTest() {
        // 1. Arrange
        Long id = 1L;
        String title = "Ingenieria de Software 3";
        String description = "Realizar el taller 2 creacion de un pipelide de despliege continuo";
        Boolean complete = false;
        LocalDate createAt = LocalDate.now();
        Task task = new Task(title, description, complete, createAt);
        task.setId(id); // Asignar el ID

        // Simula el comportamiento del Mock para findById
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        // Simula el comportamiento del Mock para delete
        doNothing().when(taskRepository).delete(task);

        // 2. Act
        Optional<TaskCreateDTO> resultOpt = taskService.deleteTask(id);

        // 3. Assert
        assertTrue(resultOpt.isPresent());
        assertEquals(title, resultOpt.get().getTitle());
        assertEquals(complete, resultOpt.get().getComplete());
        assertEquals(createAt, resultOpt.get().getCreate_at());

        // Verifica que se eliminó la tarea correcta
        verify(taskRepository).delete(task); // Verifica que se eliminó la tarea
    }

    @Test
    public void deleteTaskByIdNotContentTest(){

        // 1. Arrange
        Long id = 99L;
        String title = "Ingenieria de Software 3";
        String description = "Realizar el taller 2 creacion de un pipelide de despliege continuo";
        Boolean complete = false;
        LocalDate createAt = LocalDate.now();
        Task task = new Task(title, description, complete, createAt);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // 2. Act
        Optional<TaskCreateDTO> result = taskService.deleteTask(id);

        // 3. Assert
        assertFalse(result.isPresent());
        verify(taskRepository).findById(id);
    }

    @Test
    public void getAllTaskContentTest(){

        // 1. Asserts
        Task task1 = new Task("title1", "description", false, LocalDate.now());
        Task task2 = new Task("title2", "description", false, LocalDate.now());
        Task task3 = new Task("title3", "description", false, LocalDate.now());
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // simula el comportamiento del Mock de findAll()
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskResponseDTO> results = taskService.getAllTask();

        assertFalse(results.isEmpty());
        for(int i = 0 ; i< results.size(); i++){
            assertEquals(tasks.get(i).getTitle(),results.get(i).getTitle());
            assertEquals(tasks.get(i).getDescription(),results.get(i).getDescription());
        }

    }


    @Test
    public void getAllTaskNotContentTest(){

        // 1. Asserts
        List<Task> tasks = new ArrayList<>();

        // simula el comportamiento del Mock de findAll()
        when(taskRepository.findAll()).thenReturn(tasks);
        List<TaskResponseDTO> results = taskService.getAllTask();
        assertTrue(results.isEmpty());
    }



    @Test
    void updateTaskByIdContentTest() {
        // Arrange
        Long id = 1L;
        String title = "Estudiar SpringBoot";
        String description = "Como se realizan las pruebas unitarias en el paquete Test";
        Boolean complete = false;

        Task original = new Task(
                "Estudiar Kubernetes",
                "Estudiaría como se gestiona los pods",
                false,
                LocalDate.now()
        );
        original.setId(id);

        TaskUptadeDTO dto = new TaskUptadeDTO(title, description, complete);

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(original));
        // Devuelve cualquier Task, pero no necesitamos stub de save() para capturarlo
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        taskService.updateTask(id, dto);

        // Assert: capturamos el Task que se le pasó a save()
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        Task saved = captor.getValue();

        assertEquals(id,        saved.getId(),          "El ID debe conservarse");
        assertEquals(title,     saved.getTitle(),       "El título debe actualizarse");
        assertEquals(description, saved.getDescription(), "La descripción debe actualizarse");
        assertEquals(complete,  saved.getComplete(),    "El estado complete debe actualizarse");
    }

    @Test
    public void updateTaskByIdNotContentTest(){

        // 1. Arrange
        Long id = 99L;
        String title = "Ingenieria de Software 3";
        String description = "Realizar el taller 2 creacion de un pipelide de despliege continuo";
        Boolean complete = false;
        TaskUptadeDTO dto = new TaskUptadeDTO(title, description, complete);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // 2. Act
        Optional<TaskResponseDTO> result = taskService.updateTask(id,dto);

        // 3. Assert
        assertFalse(result.isPresent());
        verify(taskRepository).findById(id);
    }


}
