package com.safetynet.alerts.services;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;


public interface FireStationService {

	public void createFireStation(FireStation fireStation);

	public void updateFireStation(FireStation fireStation) throws DaoException;

	public void deleteFireStationByAddress(String address) throws DaoException;

	public List<FireStation> deleteFireStationByStation(int station) throws DaoException;

}
