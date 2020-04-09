package com.safetynet.alerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireAndNumberStation;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.model.EndPointFlood;
import com.safetynet.alerts.model.EndPointPersonInfo;
import com.safetynet.alerts.model.EndPointPhoneAlert;
import com.safetynet.alerts.services.EndPointService;

@RestController("endPointController")
@RequestMapping
public class EndPointController {

	Logger logger = LoggerFactory.getLogger(EndPointController.class);


	@Autowired
	EndPointService endPointService;

	@GetMapping(value = "/communityEmail")
	EndPointEmail getEmailByCity(@RequestParam(value = "city", required = false) String city)
			throws ControllerException {
		if (city.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_COMMUNITY_EMAIL ");
		return endPointService.getEmailsByCity(city);
	}
	
	@GetMapping(value = "/childAlert")
	EndPointChildAlert getChildAlert(@RequestParam(value = "address", required = false) String address)
			throws ControllerException, DaoException {
		if (address.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_CHILD_ALERT ");
		return endPointService.getChildAlert(address);
	}
	
	
	@GetMapping(value = "/firestation")
	EndPointFireStationAndCount getPersonsByFireStation(@RequestParam(value = "stationNumber", required = false) Integer stationNumber)
			throws ControllerException, DaoException {
		if (stationNumber == null) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_PERSON_BY_FIRESTATION_AND_COUNT ");
		return endPointService.getPersonsByFireStation(stationNumber);
	}

	@GetMapping(value = "/phoneAlert")
	EndPointPhoneAlert getPhoneByStation(@RequestParam(value = "firestation", required = false) Integer firestation)
			throws ControllerException {
		if (firestation == null)  {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_PHONE_ALERT ");
		return endPointService.getPhoneByStation(firestation);
	}
	
	@GetMapping(value = "/fire")
	EndPointFireAndNumberStation getFireByAddress(@RequestParam(value = "address", required = false) String address)
			throws ControllerException, DaoException {
		if (address.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_FIRE_AND_NUMBER_STATION ");
		return endPointService.getFireByAddress(address);
	}
	
	@GetMapping(value = "/flood")
	EndPointFlood getFlood(@RequestParam(value = "stations", required = false) List<Integer> stations)
			throws ControllerException, DaoException {
		if (stations.size() == 0) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_FLOOD ");
		return endPointService.getFlood(stations);
	}
	
	@GetMapping(value = "/personInfo")
	EndPointPersonInfo getPersonInfo(@RequestParam(value = "firstName", required = false) String firstName,
									 @RequestParam(value = "lastName", required = false) String lastName)
			throws ControllerException, DaoException {
		if (lastName.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_PERSON_INFO");
		return endPointService.getPersonInfo(firstName,lastName);
	}
	
	
	
}
