package com.safetynet.alerts.services;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

public interface PersonService {

	public Person createPerson(Person person);

	public Person updatePerson(Person person) throws DaoException;
	
	public Person deletePerson(String firstName,String lastName) throws DaoException;

}
