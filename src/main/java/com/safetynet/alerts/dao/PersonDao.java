package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

public interface PersonDao {


	public List<Person> getAllPerson();
	
	public void SetAllPerson(List<Person> listPerson);
			
	public void createPerson(Person person);
	
	public void updatePerson(Person person) throws DaoException;

	public void deletePerson(Person person) throws DaoException;
	
	public Person getPerson(String firstName, String lastName) throws DaoException;
	
	public void deleteAllPerson();
	
	
}
