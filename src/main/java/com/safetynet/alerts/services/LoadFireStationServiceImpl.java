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
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.FireStation;

/*
 * for loading FireStation list in memory
 * JSON file in entry
 */
@Service("loadFireStationService")
public class LoadFireStationServiceImpl implements LoadFireStationService {

	
	@Autowired
	FireStationDao fireStationDao;

	List<FireStation> listFireStation = new ArrayList<FireStation>();
	
	Logger logger = LoggerFactory.getLogger(LoadFireStationServiceImpl.class);
	
	@Value("${filename}")
	private String filename;

	/*
	 * for loading FireStation list in memory
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
			logger.error("FILE_NOT_FOUND" +filename);
		}

		// Positionnement sur le JSON fireStation et set de la liste vers FireStationDao
		String jsonFireStation = new String();
		Scanner scanner = new Scanner(fs);

		try {

			do {
				jsonFireStation = scanner.nextLine();
			} while ((jsonFireStation.contains("firestations") == false));

			while (scanner.hasNext()) {
				jsonFireStation = scanner.nextLine();
				ObjectMapper objetctMapper = new ObjectMapper();
				FireStation fireStation = objetctMapper.readValue(jsonFireStation, FireStation.class);
				listFireStation.add(fireStation);

			}
		} catch (JsonParseException e) {
			logger.info("END OF LOAD FIRESTATION");
		} finally {
			scanner.close();
		}
		// Fermeture du fichier
		try {
			fs.close();
		} catch (IOException e) {
			logger.error("PROBLEM TO CLOSE FILE" +filename);
		}
		// set DAO
		fireStationDao.SetAllFireStation(listFireStation);
	}
}
