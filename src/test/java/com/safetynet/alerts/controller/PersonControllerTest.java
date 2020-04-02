package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PersonControllerTest {

	@InjectMocks
	PersonController personController;

	@Mock
	PersonService personService;

	@Autowired
	PersonDao personDao;

	List<Person> listPerson = new ArrayList<Person>();
	Person person1 = new Person();
	Person person2 = new Person();
	Person personCreated = new Person();
	Person personUpdated = new Person();

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		personDao.deleteAllPerson();
		listPerson.clear();
	}
	
	@Test
	void whenCreatePerson(){
		person1.setAllAttributes("Toto1", "Toto1 name", "Toto1 address", "Toto1 city",
				"Toto1 zip", "Toto1 phone", "Toto1 email");
		personCreated.setAllAttributes("Totocreate", "Totocreate name", "Totocreate address", "Totocreate city",
				"Totocreate zip", "Totocreate phone", "Totocreate email");

		when(personService.createPerson(person1)).thenReturn(personCreated);
		Person result = personController.createPerson(person1);
		System.out.println("whenCreatePerson result");
		System.out.println(result.getFirstName() + " " + result.getLastName());

		assertEquals("Totocreate", result.getFirstName());
		assertEquals("Totocreate name", result.getLastName());
		assertEquals("Totocreate address", result.getAddress());
		assertEquals("Totocreate city", result.getCity());
		assertEquals("Totocreate zip", result.getZip());
		assertEquals("Totocreate phone", result.getPhone());
		assertEquals("Totocreate email", result.getEmail());
	}
	
	@Test
	void whenUpdatePerson() throws DaoException{
		person1.setAllAttributes("Toto1", "Toto1 name", "Toto1 address", "Toto1 city",
				"Toto1 zip", "Toto1 phone", "Toto1 email");
		personUpdated.setAllAttributes("Toto1", "Toto1 name", "TotoUpdated address", "TotoUpdated city",
				"TotoUpdated zip", "TotoUpdated phone", "TotoUpdated email");


		when(personService.updatePerson(person1)).thenReturn(personUpdated);
		
		Person result = personController.updatePerson(person1);
		
		assertEquals("Toto1", result.getFirstName());
		assertEquals("Toto1 name", result.getLastName());
		assertEquals("TotoUpdated address", result.getAddress());
		assertEquals("TotoUpdated city", result.getCity());
		assertEquals("TotoUpdated zip", result.getZip());
		assertEquals("TotoUpdated phone", result.getPhone());
		assertEquals("TotoUpdated email", result.getEmail());
	}

	@Test
	void whenDeletePerson() throws DaoException, ControllerException{
		person2.setAllAttributes("Toto2", "Toto2 name", "Toto2 address", "Toto2 city",
				"Toto2 zip", "Toto2 phone", "Toto2 email");


		//when(personService.deletePerson(person2)).thenReturn(person2);
		when(personService.deletePerson("Toto2", "Toto2 name")).thenReturn(person2);
		
		Person result = personController.deletePerson("Toto2", "Toto2 name");
		
		assertEquals("Toto2", result.getFirstName());
		assertEquals("Toto2 name", result.getLastName());
		assertEquals("Toto2 address", result.getAddress());
		assertEquals("Toto2 city", result.getCity());
		assertEquals("Toto2 zip", result.getZip());
		assertEquals("Toto2 phone", result.getPhone());
		assertEquals("Toto2 email", result.getEmail());
	}

}
