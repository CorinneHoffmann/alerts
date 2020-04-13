package com.safetynet.alerts.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.model.MedicalRecord;

/*
 * for loading MedicalRecord list in memory
 * JSON file in entry
 */
@Service
public class LoadMedicalRecordServiceImpl implements LoadMedicalRecordService {

	
	@Autowired
	MedicalRecordDao medicalRecordDao;

	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();
	
	Logger logger = LoggerFactory.getLogger(LoadMedicalRecordServiceImpl.class);
	
	@Value("${filename}")
	private String filename;
	
	/*
	 * for loading MedicalRecord list in memory
	 * JSON file in entry
	 */
	@Override
	@PostConstruct
	public void loadData() throws JsonMappingException, IOException, ClassNotFoundException {
		
		// Open file
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			logger.error("FILE_NOT_FOUND" + filename);
		}

		// Positionnement sur le JSON persons et set de la liste vers PersonDao
		String jsonMedicalRecord = new String();
		Scanner scanner = new Scanner(fs);

		try {

			do {
				jsonMedicalRecord = scanner.nextLine();
			} while ((jsonMedicalRecord.contains("medicalrecords") == false));

			while (scanner.hasNext()) {
				jsonMedicalRecord = scanner.nextLine();
				ObjectMapper objetctMapper = new ObjectMapper();
				MedicalRecord medicalRecord = objetctMapper.readValue(jsonMedicalRecord, MedicalRecord.class);
				listMedicalRecord.add(medicalRecord);

			}
		} catch (JsonParseException e) {
			logger.info("END OF LOAD MEDICALRECORD");
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
		medicalRecordDao.setAllMedicalRecord(listMedicalRecord);
		
	}
}
