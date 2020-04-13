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

/*
 * Controller for EndPopints
 */
@RestController("endPointController")
@RequestMapping
public class EndPointController {

	Logger logger = LoggerFactory.getLogger(EndPointController.class);


	@Autowired
	EndPointService endPointService;

	/*
	 * @param city
	 * 		String city for select in list of person
	 * @return EndPointEmail
	 * 		object EndPointEmail - email list of the selected persons
	 */
	
	@GetMapping(value = "/communityEmail")
	EndPointEmail getEmailByCity(@RequestParam(value = "city", required = false) String city)
			throws ControllerException {
		if (city.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_COMMUNITY_EMAIL ");
		return endPointService.getEmailsByCity(city);
	}
	
	/*
	 * @param address
	 * 		String address for select in list of person
	 * @return EndPointChildAlert
	 * 		object EndPointChildAlert - child list who live in this address and other persons who live in this address
	 */
	@GetMapping(value = "/childAlert")
	EndPointChildAlert getChildAlert(@RequestParam(value = "address", required = false) String address)
			throws ControllerException, DaoException {
		if (address.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_CHILD_ALERT ");
		return endPointService.getChildAlert(address);
	}
	

	/*
	 * @param stationNumber
	 * 		String stationNumber for select in list of firestation
	 * @return EndPointFireStationAndCount
	 * 		object EndPointFireStationAndCount - persons list who live in addresses covered by the firestation number and number of child and number of adults
	 */
		@GetMapping(value = "/firestation")
	EndPointFireStationAndCount getPersonsByFireStation(@RequestParam(value = "stationNumber", required = false) Integer stationNumber)
			throws ControllerException, DaoException {
		if (stationNumber == null) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_PERSON_BY_FIRESTATION_AND_COUNT ");
		return endPointService.getPersonsByFireStation(stationNumber);
	}

	/*
	 * @param firestation
	 * 		firestation number for select in list of firestation
	 * @return EndPointPhoneAlert
	 * 		object EndPointPhoneAlert - phone list of persons who live in addresses covered by firestation
	 */
	@GetMapping(value = "/phoneAlert")
	EndPointPhoneAlert getPhoneByStation(@RequestParam(value = "firestation", required = false) Integer firestation)
			throws ControllerException {
		if (firestation == null)  {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_PHONE_ALERT ");
		return endPointService.getPhoneByStation(firestation);
	}
	
	/*
	 * @param address
	 * 		String address for select in list of firestation
	 * @return EndPointFireAndNumberStation
	 * 		object EndPointFireAndNumberStation - persons list who live in addresses covered by firestation and firestation number
	 */
	@GetMapping(value = "/fire")
	EndPointFireAndNumberStation getFireByAddress(@RequestParam(value = "address", required = false) String address)
			throws ControllerException, DaoException {
		if (address.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_FIRE_AND_NUMBER_STATION ");
		return endPointService.getFireByAddress(address);
	}
	

	/*
	 * @param stations
	 * 		list of stations numbers for select in list of firestation
	 * @return EndPointFlood
	 * 		object EndPointFlood - persons list who live in addresses covered by firestation
	 */
	@GetMapping(value = "/flood")
	EndPointFlood getFlood(@RequestParam(value = "stations", required = false) List<Integer> stations)
			throws ControllerException, DaoException {
		if (stations.size() == 0) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_FLOOD ");
		return endPointService.getFlood(stations);
	}
	
	/*
	 * @param firstName and lastName
	 * 		lastName is required
	 * @return EndPointPersonInfo
	 * 		object EndPointPersonInfo - persons list with firstName and lastName
	 */
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
