package com.safetynet.alerts.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.DaoException;
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

@SpringBootTest
@ActiveProfiles("test")
class EndPointServiceIT {

	List<Person> listPerson = new ArrayList<Person>();
	List<FireStation> listFireStation = new ArrayList<FireStation>();
	List<MedicalRecord> listMedicalRecord = new ArrayList<MedicalRecord>();

	@Autowired
	Person person;
	@Autowired
	FireStation fireStation;
	@Autowired
	MedicalRecord medicalRecord;
	@Autowired
	EndPointService endPointService;

	@Autowired
	PersonDao personDao;
	@Autowired
	FireStationDao fireStationDao;
	@Autowired
	MedicalRecordDao medicalRecordDao;

	@Autowired
	PersonService personService;
	@Autowired
	MedicalRecordService medicalRecordService;
	@Autowired
	FireStationService fireStationService;

	@Autowired
	LoadPersonService loadPersonService;
	@Autowired
	LoadMedicalRecordService loadMedicalRecordService;
	@Autowired
	LoadFireStationService loadFireStationService;

	@Autowired
	EndPointEmail endPointEmail;
	@Autowired
	EndPointChildAlert endPointChildAlert;
	@Autowired
	EndPointFireStationAndCount endPointFireStationAndCount;
	@Autowired
	EndPointPhoneAlert endPointPhoneAlert;
	@Autowired
	EndPointFireAndNumberStation endPointFireAndNumberStation;
	@Autowired
	EndPointFlood endPointFlood;
	@Autowired
	EndPointPersonInfo endPointPersonInfo;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
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
	void getEmailsByCityTest() {
		String city = "city1";
		endPointEmail = endPointService.getEmailsByCity(city);
		assertTrue(endPointEmail.getListEmail().size() == 2);
		assertEquals("Toto1@email.com", endPointEmail.getListEmail().get(0));
	}

	@Test
	void getChildAlertTest() throws DaoException {
		String address = "address1";
		endPointChildAlert = endPointService.getChildAlert(address);

		assertTrue(endPointChildAlert.getEndPointChildAlertList().size() == 1);
		assertEquals("Toto1Child", endPointChildAlert.getEndPointChildAlertList().get(0).getFirstName());
	}

	@Test
	void whenNoMedicalRecordTest() throws DaoException {
		String address = "address4";
		endPointChildAlert = endPointService.getChildAlert(address);
		assertTrue(endPointChildAlert.getEndPointChildAlertList().size() == 0);

	}

	@Test
	void getPersonsByFireStationTest() throws DaoException {
		int stationNumber = 3;
		endPointFireStationAndCount = endPointService.getPersonsByFireStation(stationNumber);
		assertTrue(endPointFireStationAndCount.getEndPointFireStationList().size() == 4);
		assertEquals("Toto1", endPointFireStationAndCount.getEndPointFireStationList().get(0).getFirstName());
		assertEquals("Toto2", endPointFireStationAndCount.getEndPointFireStationList().get(1).getFirstName());
		assertEquals("Toto1Child", endPointFireStationAndCount.getEndPointFireStationList().get(2).getFirstName());
		assertEquals("Toto3", endPointFireStationAndCount.getEndPointFireStationList().get(3).getFirstName());
	}

	@Test
	void getPhoneByStationTest() throws DaoException {
		int stationNumber = 3;
		endPointPhoneAlert = endPointService.getPhoneByStation(stationNumber);
		assertTrue(endPointPhoneAlert.getListPhone().size() == 3);
		assertEquals("Toto3 phone", endPointPhoneAlert.getListPhone().get(0));
		assertEquals("Toto1 phone", endPointPhoneAlert.getListPhone().get(1));
		assertEquals("Toto2 phone", endPointPhoneAlert.getListPhone().get(2));
	}

	@Test
	void getFireByAddressTest() throws DaoException {
		String address = "address1";
		endPointFireAndNumberStation = endPointService.getFireByAddress(address);

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
	void getFireByAddressButNoMedicalRecordTest() throws DaoException {
		String address = "address4";
		endPointFireAndNumberStation = endPointService.getFireByAddress(address);
		// System.out.println(endPointChildAlert.getEndPointChildAlertList().get(0).getFirstName());
		// System.out.println(endPointFireAndNumberStation.getEndPointFireList().size());

		assertTrue(endPointFireAndNumberStation.getStation() == 6);
		assertTrue(endPointFireAndNumberStation.getEndPointFireList().size() == 2);
		assertEquals("Toto5Child", endPointFireAndNumberStation.getEndPointFireList().get(1).getFirstName());
		assertEquals("Toto5 name", endPointFireAndNumberStation.getEndPointFireList().get(1).getLastName());
		assertEquals("Toto5 phone", endPointFireAndNumberStation.getEndPointFireList().get(1).getPhone());
		assertEquals(0, endPointFireAndNumberStation.getEndPointFireList().get(1).getAge());
	}

	@Test
	void getFloodTest() throws DaoException {
		List<Integer> stations = new ArrayList<Integer>();
		stations.add(5);
		stations.add(6);

		endPointFlood = endPointService.getFlood(stations);
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
	void getPersonInfoTest() throws DaoException {
	
		String firstName ="Toto1"; 
		String lastName = "Toto1 name"; 
	
		endPointPersonInfo = endPointService.getPersonInfo(firstName,lastName);

		assertTrue(endPointPersonInfo.getEndPointPersonInfoList().size() == 1);
		assertEquals("Toto1 name",endPointPersonInfo.getEndPointPersonInfoList().get(0).getLastName());	

	}
}
