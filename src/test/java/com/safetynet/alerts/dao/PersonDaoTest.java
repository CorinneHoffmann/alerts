package com.safetynet.alerts.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.LoadPersonService;

@SpringBootTest
@ActiveProfiles("test")

class PersonDaoTest {

	List<Person> listPerson = new ArrayList<Person>();

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	LoadPersonService loadPersonService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
		personDao.deleteAllPerson();
		loadPersonService.loadData();
		listPerson = personDao.getAllPerson();
	}

	@Test
	void loadDataTest() {
		assertEquals(8, personDao.getAllPerson().size());
		assertEquals("Toto1", personDao.getAllPerson().get(0).getFirstName());
	}

	@Test
	void createPersonTest() throws DaoCreationException {

		person.setAllAttributes("Totocreate", "Toto3create name", "Totocreate address", "Totocreate city",
				"Totocreate zip", "Totocreate phone", "Totocreate email");
		personDao.createPerson(person);
		assertEquals("Totocreate", listPerson.get(8).getFirstName());
		assertEquals(9, listPerson.size());
	}

	@Test
	void updatePersonTest() throws DaoException {

		person = listPerson.get(0);
		person.setAddress("updateaddresse1");
		person.setEmail("updateToto1@email.com");
		personDao.updatePerson(person);
		assertEquals("updateaddresse1", listPerson.get(0).getAddress());
		assertEquals("updateToto1@email.com", listPerson.get(0).getEmail());
	}

	@Test
	void deletePersonTest() throws DaoException {
		personDao.deletePerson("Toto1","Toto1 name");
		assertEquals(7, listPerson.size());
		assertEquals("Toto2", listPerson.get(0).getFirstName());
	}

	@Test
	void getPersonTest() throws DaoException {

		person = personDao.getPerson("Toto4", "Toto4 name");
	
		assertEquals("Toto4", person.getFirstName());
		assertEquals("Toto4 name", person.getLastName());
	}

	@Test
	void deleteAllPersonTest() throws DaoException {
		personDao.deleteAllPerson();
		assertEquals(0, listPerson.size());
	}

	@Test
	void getAllPersonTest() throws DaoException {
		assertEquals(8, listPerson.size());
		assertEquals("Toto6", personDao.getAllPerson().get(5).getFirstName());
	}
}
