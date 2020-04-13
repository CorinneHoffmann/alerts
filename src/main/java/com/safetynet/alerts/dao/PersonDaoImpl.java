package com.safetynet.alerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

/*
 * 
 */

@Repository("personDAO")
public class PersonDaoImpl implements PersonDao {

	@Autowired
	Person person;

	List<Person> listPerson;
	String firstName;
	String lastName;
	List<String> listMembers;

	Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

	private String addres;

	

	/*
	 * Get All Persons - permit to get all the list of loaded persons
	 *@return list of Person 
	 */
	@Override
	public List<Person> getAllPerson() {
		return listPerson;
	}

	/*
	 * Set All Persons - permit to load in memory all person from JSON file
	 */
	@Override
	public void SetAllPerson(List<Person> listPerson) {
		this.listPerson = listPerson;
	}

	/*
	 * @param Person
	 * 		object person for creating
	 * @return Person
	 * 		object Person created
	 */
	@Override
	public Person createPerson(Person person) throws DaoCreationException {
		this.person = person;
		int index;
		boolean findOK = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(person.getFirstName()) == true)
					&& (listPerson.get(index).getLastName().contentEquals(person.getLastName()) == true)) {
				findOK = true;
			}
		}
		if (findOK == true) {
			logger.error("RESPONSE_PERSON_ALREADY_EXISTS " + person.getFirstName() + " " + person.getLastName());
			throw new DaoCreationException("La person que vous voulez creer existe deja");
		}
		logger.info("RESPONSE_PERSON_CREATED " + person.getFirstName() + " " + person.getLastName());
		listPerson.add(person);
		return person;
	}

	/*
	 * @param Person
	 * 		object person for update
	 * @return Person
	 * 		object Person updated
	 */
	@Override
	public Person updatePerson(Person person) throws DaoException {
		this.person = person;
		int index;
		boolean findOK = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(person.getFirstName()) == true)
					&& (listPerson.get(index).getLastName().contentEquals(person.getLastName()) == true)) {
				listPerson.set(index, person);
				person = listPerson.get(index);
				findOK = true;
			}
		}
		if (findOK == false) {
			logger.error("RESPONSE_PERSON_NOT_FOUND_FOR_UPDATE " + person.getFirstName() + " " + person.getLastName());
			throw new DaoException("La person que vous voulez modifier n\'est pas dans la liste");
		}
		logger.info("RESPONSE_PERSON_UPDATED " + person.getFirstName() + " " + person.getLastName());
		return person;
	}
	
	/*
	 * Delete All Persons loaded in memory
	 */
	@Override
	public void deleteAllPerson() {
		listPerson.clear();
	}

	/*
	 * @param firstName lastName
	 * 		firstName and lastName of the person for get
	 * @return Person
	 * 		object Person 
	 */
	@Override
	public Person getPerson(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;

		int index = 0;
		boolean findOK = false;

		while ((index < listPerson.size()) && (findOK == false)) {
			if ((listPerson.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listPerson.get(index).getLastName().contentEquals(lastName) == true)) {
				findOK = true;
				person = listPerson.get(index);
			} else {
				index++;
			}
		}

		if (findOK == false) {
			throw new DaoException("La person que vous voulez récupérer n\'est pas dans la liste");
		}
		return person;

	}
	
	/*
	 * @param firstName lastName
	 * 		firstName and lastName of the person for delete
	 * @return Person
	 * 		object Person deleted
	 */
	@Override
	public Person deletePerson(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;
		int index;
		boolean findOK = false;

		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listPerson.get(index).getLastName().contentEquals(lastName) == true)) {
				person = listPerson.get(index);
				listPerson.remove(index);
				findOK = true;
				index--;
			}
		}

		if (findOK == false) {
			logger.error("RESPONSE_PERSON_NOT_FOUND_FOR_DELETE " + firstName + " " + lastName);
			throw new DaoException("La person que vous voulez supprimer n\'est pas dans la liste");
		}
		logger.info("RESPONSE_PERSON_DELETED " + firstName + " " + lastName);
		return person;
	}
	

	/*
	 * @param address and firstName
	 * 		address and lastName of the person getting family
	 * @return list of Person who live at the same address with a different firstName
	 */
	@Override
	public List<Person> GetFamilly(String address, String firstName) {
		this.addres = address;
		this.firstName = firstName;
		int index;
		boolean findOK = false;
		List<Person> listMembers = new ArrayList<Person>();
		for (index = 0; index < listPerson.size(); index++) {
			if ((listPerson.get(index).getAddress().contentEquals(address) == true) &&
				(listPerson.get(index).getFirstName().contentEquals(firstName) == false))	
			{
				person = listPerson.get(index);
				listMembers.add(person);
				findOK = true;
			}
		}
		return listMembers;
	}

}
