package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.ControllerPersonException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.PersonService;

@RestController("personController")
@RequestMapping("/person")
public class PersonController {

	Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	@Autowired
	PersonService personService;

	//@GetMapping(value = "/person")
	@GetMapping
	List<Person> list() {
		return personDao.getAllPerson();
	}

	//@PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public void createPerson(@RequestBody Person person) {
		logger.info("CREATE_PERSON " + person.getFirstName() + " " + person.getLastName());
		personService.createPerson(person);
	}
	
	
	//@PutMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping
	public void updatePerson(@RequestBody Person person) throws DaoException {
		logger.info("UPDATE_PERSON " + person.getFirstName() + " " + person.getLastName());
		personService.updatePerson(person);
	}
	
	@DeleteMapping
	public void deletePerson(@RequestParam (required = true) String firstName,  @RequestParam (required = true) String lastName) throws DaoException, ControllerPersonException {
		
		logger.info("QUERY_DELETE_PERSON " + firstName + " " + lastName);
		personService.deletePerson(firstName,lastName);		
	}
}
		
	