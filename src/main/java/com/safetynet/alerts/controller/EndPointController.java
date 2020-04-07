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
import com.safetynet.alerts.exception.ServiceException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireStation;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.services.EndPointService;

@RestController("endPointController")
@RequestMapping
public class EndPointController {

	Logger logger = LoggerFactory.getLogger(EndPointController.class);


	@Autowired
	EndPointService endPointService;

	@GetMapping(value = "/communityEmail")
	EndPointEmail getEmailByCity(@RequestParam(value = "city", required = false) String city)
			throws ControllerException, ServiceException {
		if (city.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_COMMUNITY_EMAIL ");
		return endPointService.getEmailsByCity(city);
	}
	
	@GetMapping(value = "/childAlert")
	List<EndPointChildAlert> getChildAlert(@RequestParam(value = "address", required = false) String address)
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

}
