package com.safetynet.alerts.services;

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
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;

@SpringBootTest
@ActiveProfiles("test")
class FireStationServiceIT {

	List<FireStation> listFireStation = new ArrayList<FireStation>();

	@Autowired
	FireStation fireStation;

	@Autowired
	FireStationDao fireStationDao;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	LoadFireStationService loadFireStationService;

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
		fireStationDao.deleteAllFireStation();
		loadFireStationService.loadData();
		listFireStation = fireStationDao.getAllFireStation();
	}

	@Test
	void createFireStationServiceTest() throws DaoCreationException {

		fireStation.setAllAttributes("addresseCreate", 0);
		fireStationService.createFireStation(fireStation);
		assertEquals("addresseCreate", listFireStation.get(5).getAddress());
		assertEquals(6, listFireStation.size());
	}

	@Test
	void updateFireStationServiceTest() throws DaoException {

		fireStation = fireStationDao.getFireStation("address1");
		fireStation.setStation(4);
		fireStationService.updateFireStation(fireStation);
		assertEquals("address1", listFireStation.get(0).getAddress());
		assertEquals(4, listFireStation.get(0).getStation());
	}

	@Test
	void deleteFireStationByAddressTest() throws DaoException {
		fireStationService.deleteFireStationByAddress("address3");
		assertEquals(4, listFireStation.size());
		assertEquals("address4", listFireStation.get(2).getAddress());
	}

	@Test
	void deleteFireStationByStationTest() throws DaoException {
		fireStationService.deleteFireStationByStation(3);
		assertEquals(3, listFireStation.size());
		assertEquals("address3", listFireStation.get(0).getAddress());
	}

}
