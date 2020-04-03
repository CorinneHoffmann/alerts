package com.safetynet.alerts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoCreationException;
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
	public Person createPerson(Person person) throws DaoCreationException {
		this.person = person;
		person = personDao.createPerson(person);
		return person;
	}

	@Override
	public Person updatePerson(Person person) throws DaoException {
		this.person = person;
		person = personDao.updatePerson(person);
		return person;
	}
	
	@Override
	public Person deletePerson(String firstName,String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;
		person = personDao.deletePerson(firstName,lastName);
		return person;
	}
}
