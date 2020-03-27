package com.safetynet.alerts.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonDao personDao;

	Person person;
	List<Person> listPerson = new ArrayList<Person>();

	Logger logger = LoggerFactory.getLogger(LoadPersonServiceImpl.class);

	@Override
	public void createPerson(Person person) {
		this.person = person;
		logger.info("CREATE_PERSON");
		personDao.createPerson(person);
	}

	@Override
	public void updatePerson(Person person) throws DaoException {

		this.person = person;
		logger.info("UPDATE_PERSON");
		personDao.updatePerson(person);

	}

	@Override
	public void deletePerson(Person person) throws DaoException {
		this.person = person;
		logger.info("DELETE_PERSON");
		personDao.deletePerson(person);
	}
}
