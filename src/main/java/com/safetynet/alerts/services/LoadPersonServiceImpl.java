package com.safetynet.alerts.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Person;

/*
 * for loading person list in memory
 * JSON file in entry
 */
@Service("loadPersonService")
public class LoadPersonServiceImpl implements LoadPersonService {

	@Autowired
	PersonDao personDao;

	List<Person> listPerson = new ArrayList<Person>();

	Logger logger = LoggerFactory.getLogger(LoadPersonServiceImpl.class);

	@Value("${filename}")
	private String filename;

	/*
	 * for loading person list in memory
	 * JSON file in entry
	 */
	@PostConstruct
	@Override
	public void loadData() throws JsonMappingException, IOException, ClassNotFoundException {

		// Open file
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			logger.error("FILE_NOT_FOUND" + filename);
		}

		// Positionnement sur le JSON persons et set de la liste vers PersonDao
		String jsonPerson = new String();
		Scanner scanner = new Scanner(fs);

		try {

			do {
				jsonPerson = scanner.nextLine();
			} while ((jsonPerson.contains("persons") == false));

			while (scanner.hasNext()) {
				jsonPerson = scanner.nextLine();
				ObjectMapper objetctMapper = new ObjectMapper();
				Person person = objetctMapper.readValue(jsonPerson, Person.class);
				listPerson.add(person);

			}
		} catch (JsonParseException e) {
			logger.info("END OF LOAD PERSON");
		} finally {
			scanner.close();
		}
		// Fermeture du fichier
		try {
			fs.close();
		} catch (IOException e) {
			logger.error("PROBLEM TO CLOSE FILE" + filename);
		}
		// set DAO
		personDao.SetAllPerson(listPerson);
		
	}
}
