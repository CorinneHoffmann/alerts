package com.safetynet.alerts.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")

class FireStationServiceTest {

	@InjectMocks
	FireStationServiceImpl fireStationserviceImpl;

	@Mock
	FireStationDao fireStationDao;

	List<FireStation> listFireStation = new ArrayList<FireStation>();
	FireStation fireStation1 = new FireStation();
	FireStation fireStation2 = new FireStation();
	FireStation fireStation3 = new FireStation();
	FireStation fireStation4 = new FireStation();
	FireStation fireStationCreated = new FireStation();
	FireStation fireStationUpdated = new FireStation();

	@BeforeEach
	private void setUpPerTest() throws JsonMappingException, ClassNotFoundException, IOException, SQLException {
		fireStationDao.deleteAllFireStation();
		listFireStation.clear();

	}

	@Test
	void whenCreateFireStation() throws DaoCreationException {

		fireStationCreated.setAllAttributes("address4", 4);

		when(fireStationDao.createFireStation(fireStationCreated)).thenReturn(fireStationCreated);
		FireStation result = fireStationserviceImpl.createFireStation(fireStationCreated);

		System.out.println("whenCreateFireStation result");
		System.out.println(result.getAddress() + " " + result.getStation());

		//assertEquals(4, result.getStation());
		//assertTrue(result.getAddress() == "address4");

	}

	@Test
	void whenUpdateFireStation() throws DaoException {

		fireStation4.setAllAttributes("address5", 5);
		fireStationUpdated.setAllAttributes("address5", 3);

		System.out.println(fireStationUpdated.getAddress() + " " + fireStationUpdated.getStation());

		when(fireStationDao.updateFireStation(fireStation4)).thenReturn(fireStationUpdated);
		FireStation result = fireStationserviceImpl.updateFireStation(fireStation4);

		System.out.println("whenUpdateFireStation result");
		System.out.println(result.getAddress() + " " + result.getStation());

		//assertEquals(3, result.getStation());
		//assertTrue(result.getAddress() == "address5");

	}

	@Test
	void whenDeleteFireStationByStation() throws DaoException {

		int station = 1;
		int index;
		
		fireStation1.setAllAttributes("address1", 1);
		fireStation3.setAllAttributes("address3", 1);
		listFireStation.add(fireStation1);
		listFireStation.add(fireStation3);

		when(fireStationDao.deleteFireStationByStation(station)).thenReturn(listFireStation);

		List<FireStation> result = fireStationserviceImpl.deleteFireStationByStation(station);

		System.out.println("result");
		for (index = 0; index < result.size(); index++) {
			System.out.println(result.get(index).getAddress() + " " + result.get(index).getStation());
		}

		//assertEquals(2, result.size());

	}

	@Test
	void whenDeleteFireStationByAddress() throws DaoException {

		String address = "address2";

		fireStation2.setAllAttributes("address2", 2);

		when(fireStationDao.deleteFireStationByAddress(address)).thenReturn(fireStation2);

		FireStation result = fireStationserviceImpl.deleteFireStationByAddress(address);

		System.out.println("whenDeleteFireStationByAddress result");
		System.out.println(result.getAddress() + " " + result.getStation());

		//assertTrue(result.getAddress() == "address2");
		//assertEquals(2, result.getStation());

	}

}
