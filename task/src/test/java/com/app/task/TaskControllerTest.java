package com.app.task;

import com.app.task.controller.TaskController;
import com.app.task.model.dto.TaskAddDTO;
import com.app.task.model.dto.TaskCreateDTO;
import com.app.task.model.dto.TaskResponseDTO;
import com.app.task.model.dto.TaskUptadeDTO;
import com.app.task.model.entity.Task;
import com.app.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class) // Especifica que controlador
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private TaskService taskService;

    @Test
    public void createNewTaskTest() throws Exception {
        // 1. Preparación (Arrange)
        String title = "Estudiar Patrones de diseño";
        String description = "Estudiar el patron de diseño memento";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        Task createTask = new Task(title,description,complete,create_at); // El ID es null antes de crear
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO(title,complete,create_at);

        // Define el comportamiento del mock: cuando se llame a crearTarea con cualquier Tarea, devuelve tareaCreada
        // Asegúrate que el metodo crearTarea en tu TareaService realmente recibe Tarea y devuelve Tarea
        when(taskService.addTask(any(TaskAddDTO.class))).thenReturn(taskCreateDTO);

        // 2. Actuación (Act) & 3. Verificación (Assert)
        mockMvc.perform(post("/api/tasks/add") // Simula un POST
                        .contentType(MediaType.APPLICATION_JSON)
                        // Asegúrate que el JSON coincida con el DTO o clase que espera tu Controller
                        .content("{\"title\":\"Estudiar Patrones de diseño\",\"description\":\"Estudiar el patron de diseño memento\", \"completed\": false}")
                )
                .andExpect(status().isCreated()) // Verifica código de estado 201
                .andExpect(jsonPath("$.title").value(title)) // Verifica partes del JSON de respuesta
                .andExpect(jsonPath("$.complete").value(complete));
        // Añade más jsonPath() para otros campos que devuelva tu API (completed, createdAt, etc.)
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        // 1. Arrange
        Long id = 1L;
        String title = "Estudiar Patrones de diseño";
        String description = "Estudiar el patron de diseño memento";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        TaskResponseDTO task = new TaskResponseDTO(title,description,complete,create_at);
        task.setId(id);
        when(taskService.getTask(eq(id))).thenReturn(Optional.of(task)); // Usa eq() para valores específicos

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/tasks/{id}", id)) // Simula un GET con PathVariable
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.description").value(description));
    }

    @Test
    void getNotFoundNoTaksTest() throws Exception {
        // 1. Arrange
        Long id = 99L;
        when(taskService.getTask(eq(id))).thenReturn(Optional.empty()); // Simula que no se encuentra

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isNotFound()); // Verifica código de estado 404
    }


    @Test
    public void getAllTaskByIdTest() throws Exception {
        // 1. Arrange
        String title = "Estudiar Patrones de diseño";
        String description = "Estudiar el patron de diseño memento";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        TaskResponseDTO task1 = new TaskResponseDTO(title,description,complete,create_at);
        TaskResponseDTO task2 = new TaskResponseDTO(title,description,complete,create_at);
        TaskResponseDTO task3 = new TaskResponseDTO(title,description,complete,create_at);
        List<TaskResponseDTO> tasks = List.of(task1,task2,task3);
        when(taskService.getAllTask()).thenReturn(tasks);

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/tasks/all")) // Simula un GET con PathVariable
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].description").value(description))
                .andExpect(jsonPath("$[1].title").value(title))
                .andExpect(jsonPath("$[1].description").value(description))
                .andExpect(jsonPath("$[2].title").value(title))
                .andExpect(jsonPath("$[2].description").value(description));
    }

    @Test
    void getNotContentAllTaskTest() throws Exception {
        // 1. Arrange
        Long id = 99L;
        when(taskService.getAllTask()).thenReturn(new ArrayList<>()); // Simula que no se encuentra

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/tasks/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdTaskTest() throws Exception {
        // 1. Arrange
        Long id = 1L;
        String title = "Estudiar Patrones de diseño";
        String description = "Estudiar el patron de diseño memento";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        Task task = new Task(title,description,complete,create_at);
        TaskCreateDTO taskDelete = new TaskCreateDTO(title,complete,create_at);
        task.setId(id);
        when(taskService.deleteTask(eq(id))).thenReturn(Optional.of(taskDelete)); // Usa eq() para valores específicos

        // 2. Act & 3. Assert
        mockMvc.perform(delete("/api/tasks/{id}", id)) // Simula un GET con PathVariable
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.complete").value(complete));
    }

    @Test
    void NotFoundDeleteTaskTest() throws Exception {
        // 1. Arrange
        Long id = 99L;
        when(taskService.deleteTask(id)).thenReturn(Optional.empty()); // Simula que no se encuentra

        // 2. Act & 3. Assert
        mockMvc.perform(delete("/api/tasks/{id}",id))
                .andExpect(status().isNotFound()); // Verifica código de estado 404
    }

    @Test
    public void UpdateTaskTest() throws Exception {
        // 1. Preparación (Arrange)
        Long id = 1L;
        String title = "Estudiar Programacion funciona";
        String description = "Estudiar son los Stream y Las interfaces funcionales";
        Boolean complete = false;
        LocalDate create_at = LocalDate.now();
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(title,description,complete,create_at);

        // Define el comportamiento del mock: cuando se llame a actualizar con cualquier Tarea, devuelve taskResponseDTO
        when(taskService.updateTask(eq(id),any(TaskUptadeDTO.class))).thenReturn(Optional.of(taskResponseDTO));

        // 2. Actuación (Act) & 3. Verificación (Assert)
        mockMvc.perform(put("/api/tasks/{id}", id) // Simula un PUT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Estudiar Programacion funciona\",\"description\":\"Estudiar son los Stream y Las interfaces funcionales\", \"completed\": false}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.complete").value(complete));
    }

    @Test
    void NotFoundUpdateTaskTest() throws Exception {
        // 1. Arrange
        Long id = 99L;
        when(taskService.updateTask(eq(id),any(TaskUptadeDTO.class))).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        mockMvc.perform(put("/api/tasks/{id}", id) // Simula un PUT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Estudiar Programacion funciona\",\"description\":\"Estudiar son los Stream y Las interfaces funcionales\", \"completed\": false}")
                )
                .andExpect(status().isNotFound());
    }

}
