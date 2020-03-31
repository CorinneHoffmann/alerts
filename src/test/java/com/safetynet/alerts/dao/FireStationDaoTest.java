package com.safetynet.alerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.services.LoadFireStationService;

@SpringBootTest
@ActiveProfiles("test")
class FireStationDaoTest {
	
	Integer station;
	List<FireStation> listFireStation = new ArrayList<FireStation>();

	@Autowired
	FireStation fireStation;

	@Autowired
	FireStationDao fireStationDao;

	@Autowired
	LoadFireStationService loadFireStationService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		fireStationDao.deleteAllFireStation();
		loadFireStationService.loadData();
		listFireStation = fireStationDao.getAllFireStation();
	}
	
	@Test
	void loadDataTest() {
		assertEquals(5, fireStationDao.getAllFireStation().size());
		assertEquals("address1", fireStationDao.getAllFireStation().get(0).getAddress());
	}
	
	@Test
	void createFireStationTest() {

		fireStation.setAllAttributes("addresseCreate", 0);
		fireStationDao.createFireStation(fireStation);
		assertEquals("addresseCreate", listFireStation.get(5).getAddress());
		assertEquals(6, listFireStation.size());
	}
	
	@Test
	void updateFireStationTest() throws DaoException {
		fireStation = fireStationDao.getFireStation("address1");
		fireStation.setStation(4);
		fireStationDao.updateFireStation(fireStation);
		assertEquals("address1", listFireStation.get(0).getAddress());
		assertEquals(4, listFireStation.get(0).getStation());
	}

	@Test
	void deleteFireStationByAddressTest() throws DaoException {
		fireStationDao.deleteFireStationByAddress("address2");
		assertEquals(4, listFireStation.size());
		assertEquals("address3", listFireStation.get(1).getAddress());
	}

	@Test
	void deleteFireStationByStationTest() throws DaoException {
		fireStationDao.deleteFireStationByStation(3);
		assertEquals(3, listFireStation.size());
		assertEquals("address3", listFireStation.get(0).getAddress());
	}

	@Test
	void getFireStationTest() throws DaoException {

		fireStation = fireStationDao.getFireStation("address4");
		assertEquals(6, fireStation.getStation());
		assertEquals("address4", fireStation.getAddress());
	}

	@Test
	void deleteAllFireStationTest() throws DaoException {
		fireStationDao.deleteAllFireStation();
		assertEquals(0, listFireStation.size());
	}

	@Test
	void getAllFireStationTest() throws DaoException {
		assertEquals(5, listFireStation.size());
		assertEquals("address5", listFireStation.get(4).getAddress());
	}

}
