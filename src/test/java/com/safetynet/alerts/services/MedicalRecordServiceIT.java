package com.safetynet.alerts.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;

@SpringBootTest
@ActiveProfiles("test")
class MedicalRecordServiceIT {

	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();

	@Autowired
	MedicalRecord medicalRecord;

	@Autowired
	MedicalRecordDao medicalRecordDao;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	LoadMedicalRecordService loadMedicalRecordService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException{
		medicalRecordDao.deleteAllMedicalRecord();
		loadMedicalRecordService.loadData();
		listMedicalRecord = medicalRecordDao.getAllMedicalRecord();
	}
	
	@Test
	void createMedicalRecordServiceTest() throws DaoCreationException, ParseException {

		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");		
		Date birthDay = dateFormat.parse(stringDate);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		
		medicalRecord.setFirstName("Totocreate");
		medicalRecord.setLastName("Totocreate name");
		medicalRecord.setBirthdate(birthDay);
		medicalRecord.setMedications(medications);
		medicalRecord.setAllergies(allergies);	
		
		medicalRecordService.createMedicalRecord(medicalRecord);
		assertEquals("Totocreate", listMedicalRecord.get(6).getFirstName());
		assertEquals(7, listMedicalRecord.size());
	}
	
	@Test
	void updatePersonServiceTest() throws DaoException {
	
		String medication = "hydrapermazol:100mg";		
		medicalRecord = medicalRecordDao.getMedicalRecord("Toto1", "Toto1 name");
		medicalRecord.getMedications().set(0,"hydrapermazol:100mg");
		
		Date birthDay = medicalRecord.getBirthdate();
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");		
		String stringDate = dateFormat.format(birthDay);
		
		assertEquals(medication, listMedicalRecord.get(0).getMedications().get(0));
		assertEquals("03/06/1984", stringDate);
	}
	
	@Test
	void deletePersonServiceTest() throws DaoException {
		medicalRecordService.deleteMedicalRecord("Toto1","Toto1 name");
		assertEquals(5, listMedicalRecord.size());
		assertEquals("Toto2", listMedicalRecord.get(0).getFirstName());
	}

}
