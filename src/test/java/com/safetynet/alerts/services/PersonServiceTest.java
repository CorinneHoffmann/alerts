package com.safetynet.alerts.services;

import static org.junit.Assert.assertTrue;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PersonServiceTest {
	
	@InjectMocks
	PersonService personService;
	
	@Mock
	PersonDao personDao;
	
	List<Person> listPerson = new ArrayList<Person>();
	Person person1 = new Person();
	Person person2 = new Person();
	Person person3 = new Person();
	Person person4 = new Person();
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

		when(personDao.createPerson(person1)).thenReturn(personCreated);
		Person result = personService.createPerson(person1);

		System.out.println("whenCreatePerson result");
		System.out.println(result.getFirstName() + " " + result.getLastName());

		//assertTrue(result.getFirstName() == "Totocreate");

	}
	
	@Test
	void whenUpdatePerson() throws DaoException{

		person1.setAllAttributes("Toto1", "Toto1 name", "Toto1 address", "Toto1 city",
				"Toto1 zip", "Toto1 phone", "Toto1 email");
		personUpdated.setAllAttributes("Toto1", "Toto1 name", "TotoUpdated address", "TotoUpdated city",
				"TotoUpdated zip", "TotoUpdated phone", "TotoUpdated email");

		when(personDao.updatePerson(person1)).thenReturn(personUpdated);
		Person result = personService.updatePerson(person1);

		System.out.println("whenUpdatePerson result");
		System.out.println(result.getFirstName() + " " + result.getLastName());

		//assertTrue(result.getFirstName() == "Toto1");
		//assertTrue(result.getAddress() == "TotoUpdated address");

	}
	
	@Test
	void whenDeletePerson() throws DaoException{

		person2.setAllAttributes("Toto2", "Toto2 name", "Toto2 address", "Toto2 city",
				"Toto2 zip", "Toto2 phone", "Toto2 email");
		
		when(personDao.deletePerson("Toto2", "Toto2 name")).thenReturn(person2);
		Person result = personService.deletePerson("Toto2", "Toto2 name");

		System.out.println("whenDeletePerson result");
		System.out.println(result.getFirstName() + " " + result.getLastName());

		//assertTrue(result.getFirstName() == "Toto2");
		//assertTrue(result.getAddress() == "Toto2 address");

	}

}
