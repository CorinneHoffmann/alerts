package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.FireStationService;
import com.safetynet.alerts.services.MedicalRecordService;
import com.safetynet.alerts.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EndPointControllerIT {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao fireStationDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@BeforeEach
	void SetUpEnv() {
		personDao.deleteAllPerson();
		fireStationDao.deleteAllFireStation();
		medicalRecordDao.deleteAllMedicalRecord();
	}

	@Test
	void shouldGetEmailsByCityWhenNoParameter() throws Exception {

		MvcResult result = mockmvc.perform(get("/communityEmail")).andExpect(status().isInternalServerError())
				.andDo(print()).andReturn();
	}

	@Test
	void shouldGetEmailsByCityWhenCityIsEmpty() throws Exception {

		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "")).andExpect(status().isBadRequest())
				.andDo(print()).andReturn();
	}

	@Test
	void shouldGetEmailsByCityWhenNotFoundTest() throws Exception {

		Person person = new Person();
		person.setAllAttributes("Toto2", "Toto2 name", "Toto2 address", "Toto2 city", "Toto2 zip", "Toto2 phone",
				"Toto2 email");
		personDao.createPerson(person);
		List<Person> listPerson = personDao.getAllPerson();
		System.out.println(listPerson.get(0).getFirstName());

		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "Toto1 city"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointEmail listEmail = objectMapper.readValue(contentAsString, EndPointEmail.class);

		assertTrue(listEmail.getListEmail().size() == 0);

	}

	@Test
	void shouldGetEmailsByCityTest() throws Exception {

		Person person = new Person();
		person.setAllAttributes("Toto2", "Toto2 name", "Toto2 address", "Toto2 city", "Toto2 zip", "Toto2 phone",
				"Toto2 email");
		personDao.createPerson(person);
		List<Person> listPerson = personDao.getAllPerson();
		System.out.println(listPerson.get(0).getFirstName());

		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "Toto2 city"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointEmail listEmail = objectMapper.readValue(contentAsString, EndPointEmail.class);

		assertTrue(listEmail.getListEmail().size() == 1);
		assertEquals("Toto2 email", listEmail.getListEmail().get(0));

	}
}
