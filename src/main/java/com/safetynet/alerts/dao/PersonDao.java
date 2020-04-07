package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.Person;

public interface PersonDao {

	public List<String> GetFamilly(String address, String firstName);

	public List<Person> getAllPerson();

	public void SetAllPerson(List<Person> listPerson);

	public Person createPerson(Person person) throws DaoCreationException;

	public Person updatePerson(Person person) throws DaoException;

	public Person getPerson(String firstName, String lastName) throws DaoException;
	
	public Person deletePerson(String firstName,String lastName) throws DaoException;

	public void deleteAllPerson();

}
