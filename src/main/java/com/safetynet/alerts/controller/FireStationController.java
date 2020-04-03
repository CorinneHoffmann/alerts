package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.services.FireStationService;

@RestController("fireStationController")
@RequestMapping("/firestation")
public class FireStationController {

	Logger logger = LoggerFactory.getLogger(FireStationController.class);

	@Autowired
	FireStation fireStation;

	@Autowired
	FireStationDao fireStationDao;

	@Autowired
	FireStationService fireStationService;

	@GetMapping
	public List<FireStation> list() {
		return fireStationDao.getAllFireStation();
	}

	@PostMapping
	public FireStation createFireStation(@RequestBody FireStation fireStation) throws DaoCreationException {
		logger.info("CREATE_FIRESTATION " + fireStation.getAddress() + " " + fireStation.getStation());
		return fireStationService.createFireStation(fireStation);
	}

	@PutMapping
	public FireStation updateFireStation(@RequestBody FireStation fireStation) throws DaoException {
		logger.info("UPDATE_FIRESTATION " + fireStation.getAddress() + " " + fireStation.getStation());
		return fireStationService.updateFireStation(fireStation);
	}

	@DeleteMapping(value = "/address")
	public FireStation deleteFireStationByAddress(@RequestParam(value = "address", required = false) String address) throws DaoException, ControllerException {
		if (address.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("DELETE_FIRESTATION " + address);
		return fireStationService.deleteFireStationByAddress(address);
		
	}

	@DeleteMapping(value = "/station")
	public List<FireStation> deleteFireStationByStation(@RequestParam(value = "station", required = false) Integer station) throws DaoException, ControllerException {
		if (station == null) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("DELETE_FIRESTATION " + station);
		return fireStationService.deleteFireStationByStation(station);
	}

}
