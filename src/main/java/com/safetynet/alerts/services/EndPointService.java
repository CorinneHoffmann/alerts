package com.safetynet.alerts.services;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.exception.ServiceException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireStationAndCount;


public interface EndPointService {
	
	public EndPointEmail getEmailsByCity(String city) throws ServiceException;
	
	public List<EndPointChildAlert> getChildAlert(String address) throws DaoException;
	
	public EndPointFireStationAndCount getPersonsByFireStation(int stationNumber) throws DaoException;
	
}
