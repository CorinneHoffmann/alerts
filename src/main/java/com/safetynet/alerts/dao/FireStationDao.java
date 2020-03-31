package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;

public interface FireStationDao {

	public List<FireStation> getAllFireStation();

	public void SetAllFireStation(List<FireStation> fireStation);

	public void createFireStation(FireStation fireStation);

	public void updateFireStation(FireStation fireStation) throws DaoException;

	public List<FireStation> deleteFireStationByStation(int station) throws DaoException;
	
	public void deleteFireStationByAddress(String address) throws DaoException;

	public FireStation getFireStation(String address) throws DaoException;

	public void deleteAllFireStation();
}
