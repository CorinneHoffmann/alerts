package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireAndNumberStation;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.model.EndPointFlood;
import com.safetynet.alerts.model.EndPointPersonInfo;
import com.safetynet.alerts.model.EndPointPhoneAlert;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.services.FireStationService;
import com.safetynet.alerts.services.LoadFireStationService;
import com.safetynet.alerts.services.LoadMedicalRecordService;
import com.safetynet.alerts.services.LoadPersonService;
import com.safetynet.alerts.services.MedicalRecordService;
import com.safetynet.alerts.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EndPointControllerIT {

	@Autowired
	private MockMvc mockmvc;

	List<Person> listPerson = new ArrayList<Person>();
	List<FireStation> listFireStation = new ArrayList<FireStation>();
	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();

	@Autowired
	private PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	LoadPersonService loadPersonService;
	@Autowired
	LoadMedicalRecordService loadMedicalRecordService;
	@Autowired
	LoadFireStationService loadFireStationService;
	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao fireStationDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@BeforeEach
	void SetUpEnv() throws JsonMappingException, ClassNotFoundException, IOException {
		personDao.deleteAllPerson();
		fireStationDao.deleteAllFireStation();
		medicalRecordDao.deleteAllMedicalRecord();
		loadPersonService.loadData();
		loadFireStationService.loadData();
		loadMedicalRecordService.loadData();
		listPerson = personDao.getAllPerson();
		listFireStation = fireStationDao.getAllFireStation();
		listMedicalRecord = medicalRecordDao.getAllMedicalRecord();

	}

	@Test
	void shouldGetEmailsByCityWhenNoParameter() throws Exception {

		MvcResult result = mockmvc.perform(get("/communityEmail")).andExpect(status().isInternalServerError())
				.andDo(print()).andReturn();
	}

	@Test
	void shouldGetEmailsByCityWhenCityIsEmpty() throws Exception {

		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "")).andExpect(status().isBadRequest())
				.andDo(print()).andReturn();
	}

	@Test
	void shouldGetEmailsByCityWhenNotFoundTest() throws Exception {

		personDao.deleteAllPerson();
		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "city1")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointEmail listEmail = objectMapper.readValue(contentAsString, EndPointEmail.class);

		assertTrue(listEmail.getListEmail().size() == 0);

	}

	@Test
	void shouldGetEmailsByCityTest() throws Exception {

		System.out.println(listPerson.get(0).getFirstName());

		MvcResult result = mockmvc.perform(get("/communityEmail").param("city", "city1")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointEmail endPointEmail = objectMapper.readValue(contentAsString, EndPointEmail.class);

		assertTrue(endPointEmail.getListEmail().size() == 2);
		assertEquals("Toto1@email.com", endPointEmail.getListEmail().get(0));
		assertEquals("Toto2@email.com", endPointEmail.getListEmail().get(1));

	}

	@Test
	void shouldgetChildAlertTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/childAlert").param("address", "address1")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointChildAlert endPointChildAlert = objectMapper.readValue(contentAsString, EndPointChildAlert.class);

		assertTrue(endPointChildAlert.getEndPointChildAlertList().size() == 1);
		assertEquals("Toto1Child", endPointChildAlert.getEndPointChildAlertList().get(0).getFirstName());
		assertEquals("Toto1", endPointChildAlert.getEndPointChildAlertList().get(0).getFirstNameMembers().get(0));
		assertEquals("Toto2", endPointChildAlert.getEndPointChildAlertList().get(0).getFirstNameMembers().get(1));

	}

	@Test
	void shouldgetPersonsByFireStationTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/firestation").param("stationNumber", "3")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointFireStationAndCount endPointFireStationAndCount = objectMapper.readValue(contentAsString,
				EndPointFireStationAndCount.class);

		assertTrue(endPointFireStationAndCount.getEndPointFireStationList().size() == 4);
		assertEquals("Toto1", endPointFireStationAndCount.getEndPointFireStationList().get(0).getFirstName());
		assertEquals("Toto2", endPointFireStationAndCount.getEndPointFireStationList().get(1).getFirstName());
		assertEquals("Toto1Child", endPointFireStationAndCount.getEndPointFireStationList().get(2).getFirstName());
		assertEquals("Toto3", endPointFireStationAndCount.getEndPointFireStationList().get(3).getFirstName());

	}

	@Test
	void shouldgetPhoneByStationTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/phoneAlert").param("firestation", "3")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointPhoneAlert endPointPhoneAlert = objectMapper.readValue(contentAsString, EndPointPhoneAlert.class);

		assertTrue(endPointPhoneAlert.getListPhone().size() == 3);
		assertEquals("Toto3 phone", endPointPhoneAlert.getListPhone().get(0));
		assertEquals("Toto1 phone", endPointPhoneAlert.getListPhone().get(1));
		assertEquals("Toto2 phone", endPointPhoneAlert.getListPhone().get(2));
	}

	@Test
	void shouldgetFireByAddressTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/fire").param("address", "address1")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointFireAndNumberStation endPointFireAndNumberStation = objectMapper.readValue(contentAsString,
				EndPointFireAndNumberStation.class);

		assertTrue(endPointFireAndNumberStation.getStation() == 3);
		assertTrue(endPointFireAndNumberStation.getEndPointFireList().size() == 3);
		assertEquals("Toto1", endPointFireAndNumberStation.getEndPointFireList().get(0).getFirstName());
		assertEquals("Toto1 name", endPointFireAndNumberStation.getEndPointFireList().get(0).getLastName());
		assertEquals("Toto1 phone", endPointFireAndNumberStation.getEndPointFireList().get(0).getPhone());
		assertTrue(endPointFireAndNumberStation.getEndPointFireList().get(0).getAge() == 35);
		assertTrue(endPointFireAndNumberStation.getEndPointFireList().get(0).getAllergies().size() == 1);
		assertEquals("nillacilan", endPointFireAndNumberStation.getEndPointFireList().get(0).getAllergies().get(0));
		assertTrue(endPointFireAndNumberStation.getEndPointFireList().get(0).getMedications().size() == 2);
		assertEquals("aznol:350mg", endPointFireAndNumberStation.getEndPointFireList().get(0).getMedications().get(0));
		assertEquals("hydrapermazol:100mg",
				endPointFireAndNumberStation.getEndPointFireList().get(0).getMedications().get(1));
	}

	@Test
	void shouldgetFloodTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/flood").param("stations", "5,6")).andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointFlood endPointFlood = objectMapper.readValue(contentAsString, EndPointFlood.class);

		assertTrue(endPointFlood.getEndPointFireAndNumberStation().size() == 2);
		assertEquals(5, endPointFlood.getEndPointFireAndNumberStation().get(0).getStation());
		assertEquals(6, endPointFlood.getEndPointFireAndNumberStation().get(1).getStation());
		assertTrue(endPointFlood.getEndPointFireAndNumberStation().get(0).getEndPointFireList().size() == 1);
		assertEquals("Toto4",
				endPointFlood.getEndPointFireAndNumberStation().get(0).getEndPointFireList().get(0).getFirstName());
		assertEquals(6, endPointFlood.getEndPointFireAndNumberStation().get(1).getStation());
		assertTrue(endPointFlood.getEndPointFireAndNumberStation().get(1).getEndPointFireList().size() == 3);
		assertEquals("Toto6",
				endPointFlood.getEndPointFireAndNumberStation().get(1).getEndPointFireList().get(2).getFirstName());
		assertEquals(30, endPointFlood.getEndPointFireAndNumberStation().get(1).getEndPointFireList().get(2).getAge());
	}

	@Test
	void shouldgetPersonInfoTest() throws Exception {

		MvcResult result = mockmvc
				.perform(get("/personInfo").param("firstName", "Toto1").param("lastName", "Toto1 name"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointPersonInfo endPointPersonInfo = objectMapper.readValue(contentAsString, EndPointPersonInfo.class);

		assertTrue(endPointPersonInfo.getEndPointPersonInfoList().size() == 1);
		assertEquals("Toto1 name", endPointPersonInfo.getEndPointPersonInfoList().get(0).getLastName());
	}

	@Test
	void shouldgetPersonInfoWithOnlyParamLastNameTest() throws Exception {

		MvcResult result = mockmvc.perform(get("/personInfo").param("firstName", "").param("lastName", "Toto1 name"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		System.out.println(contentAsString);
		ObjectMapper objectMapper = new ObjectMapper();

		EndPointPersonInfo endPointPersonInfo = objectMapper.readValue(contentAsString, EndPointPersonInfo.class);

		assertTrue(endPointPersonInfo.getEndPointPersonInfoList().size() == 2);
		assertEquals(35, endPointPersonInfo.getEndPointPersonInfoList().get(0).getAge());
		assertEquals(9, endPointPersonInfo.getEndPointPersonInfoList().get(1).getAge());
	}
}
