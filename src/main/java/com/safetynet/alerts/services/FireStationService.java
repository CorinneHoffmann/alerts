package com.safetynet.alerts.services;

import java.util.List;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;


public interface FireStationService {

	public FireStation createFireStation(FireStation fireStation) throws DaoCreationException;

	public FireStation updateFireStation(FireStation fireStation) throws DaoException;

	public FireStation deleteFireStationByAddress(String address) throws DaoException;

	public List<FireStation> deleteFireStationByStation(int station) throws DaoException;

}
