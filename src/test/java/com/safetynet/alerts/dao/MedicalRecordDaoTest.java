package com.safetynet.alerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.LoadMedicalRecordService;

@SpringBootTest
@ActiveProfiles("test")

class MedicalRecordDaoTest {

	
	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();

	@Autowired
	MedicalRecord medicalRecord;

	@Autowired
	MedicalRecordDao medicalRecordDao;

	@Autowired
	LoadMedicalRecordService loadMedicalRecordService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
		medicalRecordDao.deleteAllMedicalRecord();
		loadMedicalRecordService.loadData();
		listMedicalRecord = medicalRecordDao.getAllMedicalRecord();
	}

	@Test
	void loadDataTest() {
		assertEquals(6, medicalRecordDao.getAllMedicalRecord().size());
		assertEquals("Toto1", medicalRecordDao.getAllMedicalRecord().get(0).getFirstName());
	}

	@Test
	void createPersonTest() throws DaoCreationException, ParseException {

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
		medicalRecordDao.createMedicalRecord(medicalRecord);
		
		assertEquals("Totocreate", listMedicalRecord.get(6).getFirstName());
		assertEquals(7, listMedicalRecord.size());
	}

	@Test
	void updatePersonTest() throws DaoException {
	
		String medication = "hydrapermazol:100mg";		
		medicalRecord = listMedicalRecord.get(0);
		medicalRecord.getMedications().set(0,"hydrapermazol:100mg");
		medicalRecordDao.updateMedicalRecord(medicalRecord);
		
		Date birthDay = medicalRecord.getBirthdate();
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");		
		String stringDate = dateFormat.format(birthDay);
		
		assertEquals(medication, listMedicalRecord.get(0).getMedications().get(0));
		assertEquals("03/06/1984", stringDate);
	}
	
	@Test
	void deleteMedicalRecordTest() throws DaoException {
		medicalRecordDao.deleteMedicalRecord("Toto1","Toto1 name");
		assertEquals(5, listMedicalRecord.size());
		assertEquals("Toto2", listMedicalRecord.get(0).getFirstName());
	}
	
	@Test
	void getMedicalRecordTest() throws DaoException {

		medicalRecord = medicalRecordDao.getMedicalRecord("Toto4", "Toto4 name");
		
		assertEquals("Toto4", medicalRecord.getFirstName());
		assertEquals("Toto4 name", medicalRecord.getLastName());
	}
	
	@Test
	void deleteAllMedicalRecordTest() throws DaoException {
		medicalRecordDao.deleteAllMedicalRecord();
		assertEquals(0, listMedicalRecord.size());
	}
	
	@Test
	void getAllMedicalRecordTest() throws DaoException {
		assertEquals(6, listMedicalRecord.size());
		assertEquals("Toto6", medicalRecordDao.getAllMedicalRecord().get(5).getFirstName());
	}

}
