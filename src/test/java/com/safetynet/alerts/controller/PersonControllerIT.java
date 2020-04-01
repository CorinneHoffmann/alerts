package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonControllerIT {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private PersonService personService;
	
	@Test
	void shouldGetPerson() throws Exception {

		String json = "{ \"firstName\":\"Toto1\", \"lastName\":\"Toto1 name\", \"address\":\"address1\", \"city\":\"city1\", \"zip\":\"Toto1 zip\", \"phone\":\"Toto1 phone\", \"email\":\"Toto1@email.com\" }";

		MvcResult result = mockmvc.perform(get("/person"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();
	}
	
	@Test
	void shouldCreatePerson() throws Exception {

		String json = "{ \"firstName\":\"Toto1\", \"lastName\":\"Toto1 name\", \"address\":\"address1\", \"city\":\"city1\", \"zip\":\"Toto1 zip\", \"phone\":\"Toto1 phone\", \"email\":\"Toto1@email.com\" }";

		MvcResult result = mockmvc.perform(post("/person")
										.contentType(MediaType.APPLICATION_JSON)
										.content(json)
										.characterEncoding("utf-8"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();
	}
	
	@Test
	void shouldCreatePersonOtherTest() throws Exception {

		Person person = personService.createPerson(new Person("Toto2", "Toto2 name", "Toto2 address","Toto2 city","Toto2 zip","Toto2 phone","Toto2 email"));
		ObjectMapper objectMapper = new ObjectMapper();
		String personJson = objectMapper.writeValueAsString(person);
		
		this.mockmvc.perform(post("/person")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(personJson))
	            .andExpect(status().isOk());
	}

	@Test
	void shouldUpdatePerson() throws Exception {

		String json = "{ \"firstName\":\"Toto1\", \"lastName\":\"Toto1 name\", \"address\":\"updatedaddress1\", \"city\":\"city1\", \"zip\":\"Toto1 zip\", \"phone\":\"Toto1 phone\", \"email\":\"Toto1@email.com\" }";

		MvcResult result = mockmvc.perform(	put("/person")
										.contentType(MediaType.APPLICATION_JSON)
										.content(json)
										.characterEncoding("utf-8"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();


	}
	@Test
	void shouldDeletePerson() throws Exception {

		String json = "{ \"firstName\":\"Toto1\", \"lastName\":\"Toto1 name\", \"address\":\"address1\", \"city\":\"city1\", \"zip\":\"Toto1 zip\", \"phone\":\"Toto1 phone\", \"email\":\"Toto1@email.com\" }";

		MvcResult result = mockmvc.perform(delete("/person")
										.param("firstName", "Toto1")
										.param("lastName", "Toto1 name")
										.characterEncoding("utf-8"))
									.andExpect(status().isOk())
									.andDo(print())
									.andReturn();
	}
	
	@Test
	void shoulNotdDeletePersonBecauseNotFound() throws Exception {

		MvcResult result = mockmvc.perform(delete("/person")
										.param("firstName", "Toto1")
										.characterEncoding("utf-8"))
									.andExpect(status().isInternalServerError())
									.andDo(print())
									.andReturn();
	}
}
