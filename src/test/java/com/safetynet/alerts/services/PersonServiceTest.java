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
class PersonServiceTest {

	List<Person> listPerson = new ArrayList<Person>();

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	PersonService personService;

	@Autowired
	LoadPersonService loadpersonService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		personDao.deleteAllPerson();
		loadpersonService.loadData();
		listPerson = personDao.getAllPerson();
	}

	@Test
	void createPersonServiceTest() {

		person.setAllAttributes("Totocreate", "Toto3create name", "Totocreate adress", "Totocreate city",
				"Totocreate zip", "Totocreate phone", "Totocreate email");
		personService.createPerson(person);
		assertEquals("Totocreate", listPerson.get(6).getFirstName());
		assertEquals(7, listPerson.size());
	}

	@Test
	void updatePersonServiceTest() throws DaoException {

		person = personDao.getPerson("Toto1", "Toto1 name");
		person.setAddress("updateadresse1");
		person.setEmail("updateToto1@email.com");
		person = personService.updatePerson(person);
		assertEquals("updateadresse1", person.getAddress());
		assertEquals("updateToto1@email.com", person.getEmail());
	}

	@Test
	void deletePersonServiceTest() throws DaoException {
		personDao.deletePerson(listPerson.get(0));
		assertEquals(5, listPerson.size());
		assertEquals("Toto2", listPerson.get(0).getFirstName());
	}

}
