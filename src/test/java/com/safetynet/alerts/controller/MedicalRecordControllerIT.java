package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordForReturnFormat;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.MedicalRecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MedicalRecordControllerIT {

	MedicalRecord medicalRecord = new MedicalRecord();
	
	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@BeforeEach
	void SetUpEnv() {
		medicalRecordDao.deleteAllMedicalRecord();
	}
	
	/*
	@Test
	void shouldGetMedicalRecordWhenEmpty() throws Exception {

		MvcResult result = mockmvc.perform(get("/medicalrecord"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();	
	}
	
	@Test
	void shouldGetMedicalRecord() throws Exception {
		
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
		
		MedicalRecord medicalRecord1 = medicalRecordService.createMedicalRecord(medicalRecord);
		
		MvcResult result = mockmvc.perform(get("/medicalrecord"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();
	}

	@Test
	void shouldCreateMedicalRecord() throws Exception {

		MedicalRecordForReturnFormat medicalRecordForReturnFormat = new MedicalRecordForReturnFormat();
		String stringDate = "03/05/2000";
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		allergies.add("peanut");
		
		medicalRecordForReturnFormat.setFirstName("Totocreate");
		medicalRecordForReturnFormat.setLastName("Totocreate name");
		medicalRecordForReturnFormat.setBirthdate(stringDate);
		medicalRecordForReturnFormat.setMedications(medications);
		medicalRecordForReturnFormat.setAllergies(allergies);		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String medicalRecordJson = objectMapper.writeValueAsString(medicalRecordForReturnFormat);
	
		this.mockmvc.perform(post("/medicalrecord")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(medicalRecordJson))
	            .andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	void shouldUpdatePerson() throws Exception {
		
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
		
		String stringDateUpdated = "03/06/1990";
		MedicalRecordForReturnFormat medicalRecordForReturnFormat = new MedicalRecordForReturnFormat();
		medicalRecordForReturnFormat.setFirstName("Totocreate");
		medicalRecordForReturnFormat.setLastName("Totocreate name");
		medicalRecordForReturnFormat.setBirthdate(stringDateUpdated);
		medicalRecordForReturnFormat.setMedications(medications);
		medicalRecordForReturnFormat.setAllergies(allergies);	
		
		ObjectMapper objectMapper = new ObjectMapper();
		String medicalRecordJson = objectMapper.writeValueAsString(medicalRecordForReturnFormat);
		
		MvcResult result = mockmvc.perform(	put("/medicalrecord")
										.contentType(MediaType.APPLICATION_JSON)
										.content(medicalRecordJson)
										.characterEncoding("utf-8"))
								.andExpect(status().isOk())
								.andDo(print())
								.andReturn();
	} 
	@Test
	void shouldNotUpdatePersonBecauseNotFound() throws Exception {
		
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
		
		String stringDateUpdated = "03/06/1990";
		MedicalRecordForReturnFormat medicalRecordForReturnFormat = new MedicalRecordForReturnFormat();
		medicalRecordForReturnFormat.setFirstName("Toto1");
		medicalRecordForReturnFormat.setLastName("Toto1 name");
		medicalRecordForReturnFormat.setBirthdate(stringDateUpdated);
		medicalRecordForReturnFormat.setMedications(medications);
		medicalRecordForReturnFormat.setAllergies(allergies);	
		
		ObjectMapper objectMapper = new ObjectMapper();
		String medicalRecordJson = objectMapper.writeValueAsString(medicalRecordForReturnFormat);
		
		MvcResult result = mockmvc.perform(	put("/medicalrecord")
										.contentType(MediaType.APPLICATION_JSON)
										.content(medicalRecordJson)
										.characterEncoding("utf-8"))
								.andExpect(status().isNotFound())
								.andDo(print())
								.andReturn();
	} 
	
	@Test
	void shouldDeleteMedicalRecord() throws Exception {

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
		
		MvcResult result = mockmvc.perform(delete("/medicalrecord")
										.param("firstName", "Totocreate")
										.param("lastName", "Totocreate name")
										.characterEncoding("utf-8"))
									.andExpect(status().isOk())
									.andDo(print())
									.andReturn();
	}	
	@Test
	void shouldNotDeleteMedicalRecordBecauseNotFound() throws Exception {

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
		
		MvcResult result = mockmvc.perform(delete("/medicalrecord")
										.param("firstName", "Toto1")
										.param("lastName", "Toto1 name")
										.characterEncoding("utf-8"))
									.andExpect(status().isNotFound())
									.andDo(print())
									.andReturn();
	}	
	
	@Test
	void shouldNotDeleteMedicalRecordBecauseNotParam() throws Exception {

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
		
		MvcResult result = mockmvc.perform(delete("/medicalrecord")
										.param("firstName", "Totocreate")
										.characterEncoding("utf-8"))
									.andExpect(status().isInternalServerError())
									.andDo(print())
									.andReturn();
	}	
	
	@Test
	void shouldNotFindCompletePath() throws Exception {
	
	MvcResult result = mockmvc.perform(get("/medicalRecord"))
			.andExpect(status().isNotFound())
			.andDo(print())
			.andReturn();	
}*/
	
	@Test
	void shouldNotCreateExistingMedicalRecord() throws Exception {

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
		
		//Creation identique 
		MedicalRecordForReturnFormat medicalRecordForReturnFormat = new MedicalRecordForReturnFormat();
		
		medicalRecordForReturnFormat.setFirstName("Totocreate");
		medicalRecordForReturnFormat.setLastName("Totocreate name");
		medicalRecordForReturnFormat.setBirthdate(stringDate);
		medicalRecordForReturnFormat.setMedications(medications);
		medicalRecordForReturnFormat.setAllergies(allergies);		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String medicalRecordJson = objectMapper.writeValueAsString(medicalRecordForReturnFormat);
	
		this.mockmvc.perform(post("/medicalrecord")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(medicalRecordJson))
	            .andExpect(status().isFound())
	            .andDo(print())
	            .andReturn();
	}
	
	
}
