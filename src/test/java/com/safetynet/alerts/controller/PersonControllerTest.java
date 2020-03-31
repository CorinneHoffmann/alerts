package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PersonControllerTest {

	@InjectMocks
	PersonController personController;

	@Autowired
	Person person;

	@Mock
	private PersonDao personDao;

	@Test
	void listAllPersonTest() {
		person.setFirstName("Name1");
		person.setLastName("Name1");
		List<Person> listPerson = new ArrayList<Person>();
		listPerson.add(person);
		person.setFirstName("Name2");
		person.setLastName("Name2");
		listPerson.add(person);

		when(personDao.getAllPerson()).thenReturn(listPerson);

		List<Person> result = personController.list();
		assertEquals(2, result.size());

	}
}
