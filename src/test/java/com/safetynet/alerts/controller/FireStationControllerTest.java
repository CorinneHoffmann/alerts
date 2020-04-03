package com.safetynet.alerts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.services.FireStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FireStationControllerTest {

	@InjectMocks
	FireStationController fireStationController;

	@Mock
	FireStationService fireStationService;

	@Autowired
	FireStationDao fireStationDao;

	List<FireStation> listFireStation = new ArrayList<FireStation>();
	FireStation fireStation1 = new FireStation();
	FireStation fireStation2 = new FireStation();
	FireStation fireStation3 = new FireStation();
	FireStation fireStation4 = new FireStation();
	FireStation fireStationCreated = new FireStation();
	FireStation fireStationUpdated = new FireStation();

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException {
		fireStationDao.deleteAllFireStation();
		listFireStation.clear();
	}

	@Test

	void whenDeleteFireStationByStation() throws DaoException, ControllerException {

		int station = 1;
		int index;

		fireStation1.setAllAttributes("address1", 1);
		fireStation3.setAllAttributes("address3", 1);
		listFireStation.add(fireStation1);
		listFireStation.add(fireStation3);

		when(fireStationService.deleteFireStationByStation(station)).thenReturn(listFireStation);

		List<FireStation> result = fireStationController.deleteFireStationByStation(station);

		System.out.println("result");
		for (index = 0; index < result.size(); index++) {
			System.out.println(result.get(index).getAddress() + " " + result.get(index).getStation());
		}

		assertEquals(2, result.size());

	}

	@Test

	void whenDeleteFireStationByAddress() throws DaoException, ControllerException {

		String address = "address2";
		fireStation2.setAllAttributes("address2", 2);

		when(fireStationService.deleteFireStationByAddress(address)).thenReturn(fireStation2);
		FireStation result = fireStationController.deleteFireStationByAddress(address);

		assertEquals("address2", result.getAddress());
		assertEquals(2, result.getStation());
	}

	@Test

	void whenCreateFireStation() throws DaoCreationException {

		fireStationCreated.setAllAttributes("address4", 4);

		when(fireStationService.createFireStation(fireStationCreated)).thenReturn(fireStationCreated);
		FireStation result = fireStationController.createFireStation(fireStationCreated);

		assertEquals("address4", result.getAddress());
		assertEquals(4, result.getStation());

	}

	@Test

	void whenUpdateFireStation() throws DaoException {

		fireStation4.setAllAttributes("address4", 4);
		fireStationUpdated.setAllAttributes("address3", 3);

		when(fireStationService.updateFireStation(fireStation4)).thenReturn(fireStationUpdated);
		FireStation result = fireStationController.updateFireStation(fireStation4);

		assertEquals("address3", result.getAddress());
		assertEquals(3, result.getStation());

	}
}
