package com.app.task;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import java.util.List;


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskIntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JdbcTemplate jdbcTemplate;



	@Test
	@Order(1)
	public void createTaskTest() throws Exception {
		List<String> titles = List.of("Estudiar Java","Estudiar Patrones de diseño","Estudiar SpringBoot");
		List<String> descriptions = List.of("Estudiar ResponseEntity","Estudiar Patron memento","Estudiar que es MockMvc");
		Boolean complete = false;

		for(int i = 0 ; i< titles.size();i++){
			String jsonRequest = String.format("{\"title\":\"%s\", \"description\":\"%s\", \"complete\":\"%s\"}"
					, titles.get(i), descriptions.get(i),complete);

			mockMvc.perform(post("/api/tasks/add")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonRequest))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.title").value(titles.get(i)))
					.andExpect(jsonPath("$.complete").value(complete));
		}
	}

	@Test
	@Order(2)
	public void getTaskTest() throws Exception {
		Integer id = 1;
		String title = "Estudiar Java";
		String description = "Estudiar ResponseEntity";
		Boolean complete = false;


		mockMvc.perform(get("/api/tasks/{id}",id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.title").value(title))
				.andExpect(jsonPath("$.description").value(description))
				.andExpect(jsonPath("$.complete").value(complete)
				);
	}

	@Test
	@Order(3)
	public void notFoundTaskTest() throws Exception {
		Integer id = 5;

		mockMvc.perform(get("/api/tasks/{id}",id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()
				);
	}


	@Test
	@Order(4)
	public void getAllTaskTest() throws Exception {
		List<String> titles = List.of("Estudiar Java","Estudiar Patrones de diseño","Estudiar SpringBoot");
		List<String> descriptions = List.of("Estudiar ResponseEntity","Estudiar Patron memento","Estudiar que es MockMvc");

		mockMvc.perform(get("/api/tasks/all")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value(titles.get(0)))
				.andExpect(jsonPath("$[0].description").value(descriptions.get(0)))
				.andExpect(jsonPath("$[1].title").value(titles.get(1)))
				.andExpect(jsonPath("$[1].description").value(descriptions.get(1)))
				.andExpect(jsonPath("$[2].title").value(titles.get(2)))
				.andExpect(jsonPath("$[2].description").value(descriptions.get(2))
				);
	}

	@Test
	@Order(5)
	public void updateTaskTest() throws Exception {
		Integer id = 2;
		String title = "Estudiar Programcion Funcional";
		String description = "Estudiar Stream y Interfaces funcionales";
		Boolean complete = false;

		String jsonRequest = String.format("{\"title\":\"%s\", \"description\":\"%s\", \"complete\":\"%s\"}"
				, title, description,complete);

		mockMvc.perform(put("/api/tasks/{id}",id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(title))
				.andExpect(jsonPath("$.description").value(description))
				.andExpect(jsonPath("$.complete").value(complete));
	}

	@Test
	@Order(6)
	public void updateNotFoundTaskTest() throws Exception {
		Integer id = 5;
		String title = "Estudiar Programcion Funcional";
		String description = "Estudiar Stream y Interfaces funcionales";
		Boolean complete = false;

		String jsonRequest = String.format("{\"title\":\"%s\", \"description\":\"%s\", \"complete\":\"%s\"}"
				, title, description,complete);

		mockMvc.perform(put("/api/tasks/{id}",id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isNotFound()
				);
	}

	@Test
	@Order(7)
	public void deleteTaskTest() throws Exception {
		List<String> titles = List.of("Estudiar Java","Estudiar Programcion Funcional","Estudiar SpringBoot");

		for(int i = 0 ; i< titles.size();i++){
			mockMvc.perform(delete("/api/tasks/{id}" ,i+1)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNoContent())
					.andExpect(jsonPath("$.title").value(titles.get(i))
					);
		}
	}

	@Test
	@Order(8)
	public void deleteNotFoundTaskTest() throws Exception {
		Integer id = 5;
		mockMvc.perform(delete("/api/tasks/{id}" ,id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()
				);
	}

	@Test
	@Order(9)
	public void notContentAllTaskTest() throws Exception {

		mockMvc.perform(get("/api/tasks/all")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()
				);
	}

}
