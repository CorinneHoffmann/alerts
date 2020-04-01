package com.safetynet.alerts.services;

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
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceIT {

	List<Person> listPerson = new ArrayList<Person>();

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	PersonService personService;

	@Autowired
	LoadPersonService loadPersonService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		personDao.deleteAllPerson();
		loadPersonService.loadData();
		listPerson = personDao.getAllPerson();
	}

	@Test
	void createPersonServiceTest() {

		person.setAllAttributes("Totocreate", "Toto3create name", "Totocreate address", "Totocreate city",
				"Totocreate zip", "Totocreate phone", "Totocreate email");
		personService.createPerson(person);
		assertEquals("Totocreate", listPerson.get(6).getFirstName());
		assertEquals(7, listPerson.size());
	}

	@Test
	void updatePersonServiceTest() throws DaoException {

		person = personDao.getPerson("Toto1", "Toto1 name");
		person.setAddress("updateaddresse1");
		person.setEmail("updateToto1@email.com");
		personService.updatePerson(person);
		assertEquals("updateaddresse1", listPerson.get(0).getAddress());
		assertEquals("updateToto1@email.com", listPerson.get(0).getEmail());
	}

	@Test
	void deletePersonServiceTest() throws DaoException {
		personService.deletePerson("Toto1","Toto1 name");
		assertEquals(5, listPerson.size());
		assertEquals("Toto2", listPerson.get(0).getFirstName());
	}

}
