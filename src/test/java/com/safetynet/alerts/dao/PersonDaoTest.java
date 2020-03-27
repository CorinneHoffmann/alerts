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
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.LoadPersonService;

@SpringBootTest
@ActiveProfiles("test")

class PersonDaoTest {
	@Autowired
	PersonDao personDao;

	@Autowired
	LoadPersonService loadpersonService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		personDao.deleteAllPerson();
		loadpersonService.loadData();
	}

	@Test
	void loadDataTest() throws JsonMappingException, IOException, ClassNotFoundException, SQLException {
		assertEquals(6, personDao.getAllPerson().size());
		assertEquals("Toto1", personDao.getAllPerson().get(0).getFirstName());

	}

	@Test
	void createPersonTest() {

		Person person = new Person();
		person.setFirstName("Totocreate");
		person.setLastName("Toto3create name");
		person.setAddress("Totocreate adress");
		person.setAddress("Totocreate city");
		person.setAddress("Totocreate zip");
		person.setAddress("Totocreate phone");
		person.setAddress("Totocreate email");
		personDao.createPerson(person);
		assertEquals(7, personDao.getAllPerson().size());
		assertEquals("Totocreate", personDao.getAllPerson().get(6).getFirstName());
	}

	@Test
	void updatePersonTest() throws DaoException {

		Person person = new Person();
		person = personDao.getPerson("Toto1", "Toto1 name");
		person.setAddress("updateadresse1");
		person.setEmail("updateToto1@email.com");
		personDao.updatePerson(person);
		assertEquals(6, personDao.getAllPerson().size());
		assertEquals("updateadresse1", personDao.getPerson("Toto1", "Toto1 name").getAddress());
		assertEquals("updateToto1@email.com", personDao.getPerson("Toto1", "Toto1 name").getEmail());
	}
	
	
	@Test
	void getPersonTest() throws DaoException {

		Person person = new Person();
		person = personDao.getPerson("Toto4", "Toto4 name");
		assertEquals(6, personDao.getAllPerson().size());
		assertEquals("Toto4", person.getFirstName());
		assertEquals("Toto4 name", person.getLastName());
	}
	
	@Test
	void deleteAllPersonTest() throws DaoException {
		personDao.deleteAllPerson();
		assertEquals(0, personDao.getAllPerson().size());
	}
	
	@Test
	void getAllPersonTest() throws DaoException {

		List<Person> listPerson;
		
		listPerson = personDao.getAllPerson();
		assertEquals(6, listPerson.size());
	}
}
