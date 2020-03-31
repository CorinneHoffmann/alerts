package com.safetynet.alerts.controller;


import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.PersonService;

@RestController("personController")

public class PersonController {

	Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	PersonService personService;

	@GetMapping(value = "/persons")
	List<Person> list() {
		return personDao.getAllPerson();
	}
	
	@PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createPerson(@RequestBody Person person) {
		logger.info("CREATE_PERSON " + person.getFirstName() + " " + person.getLastName());
		personService.createPerson(person);
	}

	@PutMapping(value = "/person")
	public void updatePerson(@RequestBody Person person) throws DaoException {
		logger.info("UPDATE_PERSON " +person.getFirstName() + " " + person.getLastName());
		person = personService.updatePerson(person);
	}

	@DeleteMapping(value = "/person")
	public void deletePerson(@RequestBody Person person) throws DaoException {
		logger.info("DELETE_PERSON " +person.getFirstName() + " " + person.getLastName());
		personService.deletePerson(person);
	}

}