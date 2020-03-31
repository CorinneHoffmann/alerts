package com.safetynet.alerts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired
	Person person;

	@Autowired
	PersonDao personDao;

	private String firstName;
	private String lastName;

	@Override
	public void createPerson(Person person) {
		this.person = person;
		personDao.createPerson(person);
	}

	@Override
	public void updatePerson(Person person) throws DaoException {
		this.person = person;
		personDao.updatePerson(person);
	}
	
	@Override
	public void deletePerson(String firstName,String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;
		personDao.deletePerson(firstName,lastName);
	}
}
