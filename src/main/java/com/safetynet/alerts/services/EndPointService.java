package com.safetynet.alerts.services;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireAndNumberStation;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.model.EndPointFlood;
import com.safetynet.alerts.model.EndPointPersonInfo;
import com.safetynet.alerts.model.EndPointPhoneAlert;


public interface EndPointService {
	
	public EndPointEmail getEmailsByCity(String city);
	
	public EndPointChildAlert getChildAlert(String address) throws DaoException;
	
	public EndPointFireStationAndCount getPersonsByFireStation(int stationNumber) throws DaoException;
	
	public EndPointPhoneAlert getPhoneByStation(int stationNumber);
	
	public EndPointFireAndNumberStation getFireByAddress(String address) throws DaoException;
	
	public EndPointFlood getFlood(List<Integer> stations);
	
	public EndPointPersonInfo getPersonInfo(String firstName, String lastName);
	
}
