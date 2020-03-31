package com.safetynet.alerts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.FireStationDao;
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
	public void createFireStation(FireStation fireStation) {
		this.fireStation = fireStation;
		fireStationDao.createFireStation(fireStation);		
	}

	@Override
	public void updateFireStation(FireStation fireStation) throws DaoException {
		this.fireStation = fireStation;
		fireStationDao.updateFireStation(fireStation);	
		
	}

	@Override
	public List<FireStation> deleteFireStationByStation(int station) throws DaoException {
		this.station = station;
		List<FireStation> listFireStation = fireStationDao.deleteFireStationByStation(station);	
		return listFireStation;
	}
	
	@Override
	public void deleteFireStationByAddress(String address) throws DaoException {
		this.address = address;
		fireStationDao.deleteFireStationByAddress(address);	
		
	}

}
