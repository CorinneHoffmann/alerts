package com.safetynet.alerts.services;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

public interface PersonService {

	//public void loadDataPerson() throws JsonMappingException, IOException;

	public void createPerson(Person person);
	
	public Person updatePerson(Person person) throws DaoException;
	
	public void deletePerson(Person person) throws DaoException;

}
