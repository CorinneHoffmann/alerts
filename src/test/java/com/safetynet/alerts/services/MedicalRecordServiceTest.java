package com.safetynet.alerts.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MedicalRecordServiceTest {

	@InjectMocks
	MedicalRecordServiceImpl medicalRecordServiceImpl;

	@Mock
	MedicalRecordDao medicalRecordDao;

	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();
	MedicalRecord medicalRecord1 = new MedicalRecord();
	MedicalRecord medicalRecordDeleted = new MedicalRecord();
	MedicalRecord medicalRecordCreated = new MedicalRecord();
	MedicalRecord medicalRecordUpdated = new MedicalRecord();

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		medicalRecordDao.deleteAllMedicalRecord();
		listMedicalRecord.clear();

	}

	@Test
	void whenCreateMedicalRecord() throws DaoCreationException, ParseException {
		
		medicalRecord1.setFirstName("Toto1");
		medicalRecord1.setLastName("Toto1 name");
		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date birthDay = dateFormat.parse(stringDate);
		medicalRecord1.setBirthdate(birthDay);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		medicalRecord1.setAllergies(allergies);
		medicalRecord1.setMedications(medications);
		
		medicalRecordCreated.setFirstName("Totocreate");
		medicalRecordCreated.setLastName("Totocreate name");
		stringDate = "03/07/2000";
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		birthDay = dateFormat.parse(stringDate);
		medicalRecordCreated.setBirthdate(birthDay);
		medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		medicalRecordCreated.setAllergies(allergies);
		medicalRecordCreated.setMedications(medications);
		
		when(medicalRecordDao.createMedicalRecord(medicalRecord1)).thenReturn(medicalRecordCreated);
		MedicalRecord result = medicalRecordServiceImpl.createMedicalRecord(medicalRecord1);

		assertTrue(result.getFirstName() == "Totocreate");
	}

	@Test
	void whenUpdateMedicalRecord() throws DaoException, ParseException {
		medicalRecord1.setFirstName("Toto1");
		medicalRecord1.setLastName("Toto1 name");
		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date birthDay = dateFormat.parse(stringDate);
		medicalRecord1.setBirthdate(birthDay);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		medicalRecord1.setAllergies(allergies);
		medicalRecord1.setMedications(medications);
		
		medicalRecordUpdated.setFirstName("Toto1");
		medicalRecordUpdated.setLastName("Toto1 name");
		stringDate = "03/07/2000";
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		birthDay = dateFormat.parse(stringDate);
		medicalRecordUpdated.setBirthdate(birthDay);
		medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		medicalRecordUpdated.setAllergies(allergies);
		medicalRecordUpdated.setMedications(medications);
		
		when(medicalRecordDao.updateMedicalRecord(medicalRecord1)).thenReturn(medicalRecordUpdated);
		MedicalRecord result = medicalRecordServiceImpl.updateMedicalRecord(medicalRecord1);

		assertEquals("03/07/2000",dateFormat.format(result.getBirthdate()));
		assertTrue(result.getFirstName() == "Toto1");
	}
	

	@Test
	void whenDeleteMedicalRecord() throws DaoException, ParseException {

		medicalRecordDeleted.setFirstName("TotoDeleted");
		medicalRecordDeleted.setLastName("TotoDeleted name");
		String stringDate = "03/05/2000";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date birthDay = dateFormat.parse(stringDate);
		medicalRecordDeleted.setBirthdate(birthDay);
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		medicalRecordDeleted.setAllergies(allergies);
		medicalRecordDeleted.setMedications(medications);
		
		when(medicalRecordDao.updateMedicalRecord(medicalRecordDeleted)).thenReturn(medicalRecordDeleted);
		MedicalRecord result = medicalRecordServiceImpl.updateMedicalRecord(medicalRecordDeleted);

		assertEquals("03/05/2000",dateFormat.format(result.getBirthdate()));
		assertTrue(result.getFirstName() == "TotoDeleted");
	}

}
