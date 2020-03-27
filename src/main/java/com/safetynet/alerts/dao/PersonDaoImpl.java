package com.safetynet.alerts.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

@Repository("personDAO")
public class PersonDaoImpl implements PersonDao {

	List<Person> listPerson;
	Person person;
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
		listPerson.add(person);
	}

	@Override
	public void updatePerson(Person person) throws DaoException {
		this.person = person;
		int index;
		boolean indPersonFind = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(person.getFirstName()) == true)
					&& (listPerson.get(index).getLastName().contentEquals(person.getLastName()) == true)) {
				listPerson.set(index, person);
				indPersonFind = true;
				logger.debug("UPDATE_PERSON person found and updated");
				System.out.println(person.getFirstName() + " " + person.getLastName());
			}
		}

		if (indPersonFind == false) {
			logger.debug("UPDATE_PERSON person not found");
			System.out.println(person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez modifier n\'est pas dans la liste");

		}

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
				logger.debug("DELETE_PERSON person found and deleted");
				System.out.println(person.getFirstName() + " " + person.getLastName());
			}
		}

		if (indPersonFind == false) {
			logger.debug("DELETE_PERSON person not found");
			System.out.println(person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez supprimer n\'est pas dans la liste");
		}
	}

	@Override
	public void deleteAllPerson() {
		logger.debug("DELETE_ALL_PERSON");
		listPerson.clear();
	}

	@Override
	public Person getPerson(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;

		Person result = new Person();

		int index = 0;
		boolean indPersonFind = false;

		while ((index < listPerson.size()) && (indPersonFind == false)) {
			if ((listPerson.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listPerson.get(index).getLastName().contentEquals(lastName) == true)) {
				indPersonFind = true;
				result = listPerson.get(index);
				logger.debug("GET_PERSON person found and returned");
			} else {
				index++;
			}
		}

		if (indPersonFind == false) {
			result = null;
			logger.debug("GET_PERSON person not found");
			System.out.println(person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez récupérer n\'est pas dans la liste");
		}

		return result;
	}
}
