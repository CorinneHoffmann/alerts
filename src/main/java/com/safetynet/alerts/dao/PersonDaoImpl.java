package com.safetynet.alerts.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@Repository("personDAO")
public class PersonDaoImpl implements PersonDao {

	@Autowired
	Person person;
	
	List<Person> listPerson;
	String firstName;
	String lastName;

	Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

	@Override
	public List<Person> getAllPerson() {
		return listPerson;
	}

	@Override
	public void SetAllPerson(List<Person> listPerson) {
		this.listPerson = listPerson;
	}

	@Override
	public void createPerson(Person person) {
		this.person = person;	
		logger.info("PERSON CREATED " +person.getFirstName() + " " + person.getLastName());
		listPerson.add(person);		
		//return listPerson;
	}

	@Override
	public Person updatePerson(Person person) throws DaoException {
		this.person = person;
		int index;
		boolean updateOK = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(person.getFirstName()) == true)
					&& (listPerson.get(index).getLastName().contentEquals(person.getLastName()) == true)) {
				listPerson.set(index, person);
				updateOK = true;
				person = listPerson.get(index);
				logger.info("PERSON UPDATED " +person.getFirstName() + " " + person.getLastName());
			}
		}

		if (updateOK == false) {
			logger.error("PERSON NOT FOUND FOR UPDATE " +person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez modifier n\'est pas dans la liste");

		}
		return person;
	}

	@Override
	public void deletePerson(Person person) throws DaoException {
		this.person = person;
		int index;
		boolean indPersonFind = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(person.getFirstName()) == true)
					&& (listPerson.get(index).getLastName().contentEquals(person.getLastName()) == true)) {
				listPerson.remove(index);
				indPersonFind = true;
				logger.info("PERSON DELETED " +person.getFirstName() + " " + person.getLastName());
			}
		}

		if (indPersonFind == false) {
			logger.error("PERSON NOT FOUND FOR DELETE " +person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez supprimer n\'est pas dans la liste");
		}
	}

	@Override
	public void deleteAllPerson() {
		listPerson.clear();
	}

	@Override
	public Person getPerson(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;


		int index = 0;
		boolean indPersonFind = false;

		while ((index < listPerson.size()) && (indPersonFind == false)) {
			if ((listPerson.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listPerson.get(index).getLastName().contentEquals(lastName) == true)) {
				indPersonFind = true;
				person = listPerson.get(index);
			} else {
				index++;
			}
		}

		if (indPersonFind == false) {
			throw new DaoException("La person que vous voulez récupérer n\'est pas dans la liste");
		}
		return person;
	}
}
