package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordForReturnFormat;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.MedicalRecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MedicalRecordControllerTest {

	@InjectMocks
	MedicalRecordController medicalRecordController;

	@Mock
	MedicalRecordService medicalRecordService;

	@Autowired
	MedicalRecordDao medicalRecordDao;

	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();
	MedicalRecord medicalRecord1 = new MedicalRecord();
	MedicalRecord medicalRecord2 = new MedicalRecord();
	MedicalRecord medicalRecordCreated = new MedicalRecord();
	MedicalRecord medicalRecordUpdated = new MedicalRecord();

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
		medicalRecordDao.deleteAllMedicalRecord();
		listMedicalRecord.clear();
	}

	@Test
	void whenCreateMedicalRecord() throws DaoCreationException, ParseException {
		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date birthDay = dateFormat.parse(stringDate);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");

		medicalRecord1.setFirstName("Toto1");
		medicalRecord1.setLastName("Toto1 name");
		medicalRecord1.setBirthdate(birthDay);
		medicalRecord1.setMedications(medications);
		medicalRecord1.setAllergies(allergies);

		medicalRecordCreated.setFirstName("Totocreate");
		medicalRecordCreated.setLastName("Totocreate name");
		medicalRecordCreated.setBirthdate(birthDay);
		medicalRecordCreated.setMedications(medications);
		medicalRecordCreated.setAllergies(allergies);

		when(medicalRecordService.createMedicalRecord(medicalRecord1)).thenReturn(medicalRecordCreated);
		MedicalRecordForReturnFormat result = medicalRecordController.createMedicalRecord(medicalRecord1);

		assertEquals("Totocreate", result.getFirstName());
		assertEquals("Totocreate name", result.getLastName());
		assertEquals(stringDate, result.getBirthdate());
	}

	@Test
	void whenUpdatePerson() throws DaoException, ParseException{
		
		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");		
		Date birthDay = dateFormat.parse(stringDate);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		
		medicalRecord1.setFirstName("Toto1");
		medicalRecord1.setLastName("Toto1 name");
		medicalRecord1.setBirthdate(birthDay);
		medicalRecord1.setMedications(medications);
		medicalRecord1.setAllergies(allergies);	
		
		String stringDateUpdated = "03/09/2000";		
		Date birthDayUpdated = dateFormat.parse(stringDateUpdated);
		
		medicalRecordUpdated.setFirstName("Toto1");
		medicalRecordUpdated.setLastName("Toto1 name");
		medicalRecordUpdated.setBirthdate(birthDayUpdated);
		medicalRecordUpdated.setMedications(medications);
		medicalRecordUpdated.setAllergies(allergies);	

		when(medicalRecordService.updateMedicalRecord(medicalRecord1)).thenReturn(medicalRecordUpdated);
		
		MedicalRecordForReturnFormat result = medicalRecordController.updateMedicalRecord(medicalRecord1);
		
		assertEquals("Toto1", result.getFirstName());
		assertEquals("Toto1 name", result.getLastName());
		assertEquals(stringDateUpdated, result.getBirthdate());
	}
	
	@Test
	void whenDeleteMedicalRecord() throws DaoException, ControllerException, ParseException{

		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date birthDay = dateFormat.parse(stringDate);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");

		medicalRecord2.setFirstName("Toto2");
		medicalRecord2.setLastName("Toto2 name");
		medicalRecord2.setBirthdate(birthDay);
		medicalRecord2.setMedications(medications);
		medicalRecord2.setAllergies(allergies);
		
		when(medicalRecordService.deleteMedicalRecord("Toto2", "Toto2 name")).thenReturn(medicalRecord2);
		
		MedicalRecordForReturnFormat result = medicalRecordController.deleteMedicalRecord("Toto2", "Toto2 name");
		
		assertEquals("Toto2", result.getFirstName());
		assertEquals("Toto2 name", result.getLastName());
		assertEquals(stringDate, result.getBirthdate());
		
	}
}
