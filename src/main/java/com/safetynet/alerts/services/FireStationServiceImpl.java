package com.safetynet.alerts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;

@Service("fireStationService")
public class FireStationServiceImpl implements FireStationService {

	
	@Autowired
	FireStation fireStation;

	@Autowired
	FireStationDao fireStationDao;

	private int station;

	private String address;
	
	@Override
	public FireStation createFireStation(FireStation fireStation) throws DaoCreationException {
		this.fireStation = fireStation;
		fireStation = fireStationDao.createFireStation(fireStation);		
		return fireStation;
	}

	@Override
	public FireStation updateFireStation(FireStation fireStation) throws DaoException {
		this.fireStation = fireStation;
		fireStation = fireStationDao.updateFireStation(fireStation);	
		return fireStation;	
	}

	@Override
	public List<FireStation> deleteFireStationByStation(int station) throws DaoException {
		this.station = station;
		List<FireStation> listFireStation = fireStationDao.deleteFireStationByStation(station);	
		return listFireStation;
	}
	
	@Override
	public FireStation deleteFireStationByAddress(String address) throws DaoException {
		this.address = address;
		fireStation = fireStationDao.deleteFireStationByAddress(address);	
		return fireStation;
	}

}
