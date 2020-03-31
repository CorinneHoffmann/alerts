package com.safetynet.alerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.FireStation;

@Repository("fireStationDao")
public class FireStationDaoImpl implements FireStationDao {

	@Autowired
	FireStation fireStation;

	List<FireStation> listFireStation;
	List<FireStation> result = new ArrayList<FireStation>();
	int idStation;

	Logger logger = LoggerFactory.getLogger(FireStationDaoImpl.class);

	private String address;

	private int station;

	@Override
	public List<FireStation> getAllFireStation() {
		return listFireStation;
	}

	@Override
	public void SetAllFireStation(List<FireStation> listFireStation) {
		this.listFireStation = listFireStation;
	}

	@Override
	public void createFireStation(FireStation fireStation) {
		this.fireStation = fireStation;
		listFireStation.add(fireStation);
		logger.info("FIRESTATION CREATED " + fireStation.getAddress() + " " + fireStation.getStation());
	}

	@Override
	public void updateFireStation(FireStation fireStation) throws DaoException {
		this.fireStation = fireStation;
		int index;
		boolean updateOK = false;
		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getAddress().contentEquals(fireStation.getAddress()) == true) {
				listFireStation.get(index).setStation(fireStation.getStation());
				logger.info("FIRESTATION UPDATED " + fireStation.getAddress() + " " + fireStation.getStation());
				updateOK = true;
			}
		}
		if (updateOK == false) {
			logger.error("FIRESTATION NOT FOUND FOR UPDATE " + fireStation.getAddress());
			throw new DaoException("La station que vous voulez modifier n\'est pas dans la liste");
		}
	}

	@Override
	public void deleteFireStationByAddress(String address) throws DaoException {
		this.address = address;
		int index;
		boolean updateOK = false;
		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getAddress().contentEquals(address) == true) {
				listFireStation.remove(index);
				logger.info("FIRESTATION DELETED " + fireStation.getAddress() + " " + fireStation.getStation());
				updateOK = true;
			}
		}
		if (updateOK == false) {
			logger.error("FIRESTATION NOT FOUND FOR DELETE " + fireStation.getAddress());
			throw new DaoException("L/'adresse de la fireStation que vous voulez supprimer n\'est pas dans la liste");
		}
	}

	@Override
	public List<FireStation> deleteFireStationByStation(int station) throws DaoException {
		this.station = station;
		int index;
		boolean updateOK = false;
		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getStation() == station) {
				fireStation = listFireStation.get(index);
				result.add(fireStation);
				listFireStation.remove(index);
				index--;
				updateOK = true;
			}
		}
		if (updateOK == true) {
			logger.info("FIRESTATION DELETED station " + fireStation.getStation());
		}
		if (updateOK == false) {
			logger.error("FIRESTATION NOT FOUND FOR DELETE " + fireStation.getStation());
			throw new DaoException("L\'id de la fireStation que vous voulez supprimer n\'est pas dans la liste");
		}
		for (index = 0; index < result.size(); index++) {
			logger.info("FIRESTATION DELETED adress " + result.get(index).getAddress() + "station  "
					+ result.get(index).getStation());
		}
		return result;
	}

	@Override
	public FireStation getFireStation(String address) throws DaoException {
		this.address = address;
		int index = 0;
		boolean findOK = false;

		while ((index < listFireStation.size()) && (findOK == false)) {
			if (listFireStation.get(index).getAddress().contentEquals(address) == true) {
				findOK = true;
				fireStation = listFireStation.get(index);
			} else {
				index++;
			}
		}
		if (findOK == false) {
			throw new DaoException("La FireStation que vous voulez récupérer n\'est pas dans la liste");
		}
		return fireStation;
	}

	@Override
	public void deleteAllFireStation() {
		listFireStation.clear();

	}

}
