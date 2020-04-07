package com.safetynet.alerts.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.dao.PersonDaoImpl;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.exception.ServiceException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireStation;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.sefetynet.alerts.util.AgeCalculator;

@Service("endPointService")
public class EndPointServiceImpl implements EndPointService {

	@Autowired
	PersonDao personDao;

	@Autowired
	MedicalRecordDao medicalRecordDao;

	@Autowired
	FireStationDao fireStationDao;

	@Autowired
	EndPointEmail endPointEmail;

	private String city;

	Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

	private String address;

	private int stationNumber;

	private EndPointFireStation endPointFireStation;

	@Override
	public EndPointEmail getEmailsByCity(String city) throws ServiceException {
		this.city = city;
		List<Person> listPerson = personDao.getAllPerson();
		Set<String> setEmail = new HashSet<>();
		int index;
		boolean findOk = false;
		for (index = 0; index < listPerson.size(); index++) {
			if (listPerson.get(index).getCity().contentEquals(city) == true) {
				setEmail.add(listPerson.get(index).getEmail());
				findOk = true;
			}
		}
		if (findOk == false) {
			logger.error("RESPONSE_END_POINT_EMAIL_CITY_NOT_FOUND " + city);
			throw new ServiceException("La ville n\'est pas dans la liste des personnes");
		}
		logger.info("RESPONSE_END_POINT_EMAIL " + city);
		List<String> listEmail = setEmail.stream().collect(Collectors.toList());
		endPointEmail.setListEmail(listEmail);
		return endPointEmail;

	}

	@Override
	public List<EndPointChildAlert> getChildAlert(String address) throws DaoException {
		this.address = address;
		List<EndPointChildAlert> listEndPointChildAlert = new ArrayList<EndPointChildAlert>();

		List<Person> listPerson = personDao.getAllPerson();
		List<Person> listMembers = new ArrayList<Person>();

		AgeCalculator ac = new AgeCalculator();
		int index;
		int indexM;
		boolean findOk = false;
		int age = 0;

		for (index = 0; index < listPerson.size(); index++) {
			if (listPerson.get(index).getAddress().contentEquals(address) == true) {
				try {
					MedicalRecord mr = medicalRecordDao.getMedicalRecord(listPerson.get(index).getFirstName(),
							listPerson.get(index).getLastName());
					age = ac.calculateAge(mr.getBirthdate());
					if (age < 18) {
						EndPointChildAlert endPointChildAlert = new EndPointChildAlert();
						endPointChildAlert.setFirstName(listPerson.get(index).getFirstName());
						endPointChildAlert.setLastName(listPerson.get(index).getLastName());
						endPointChildAlert.setAge(age);
						listMembers = personDao.GetFamilly(address, listPerson.get(index).getFirstName());
						List<String> listMemberFirstName = new ArrayList<String>();
						for (indexM = 0; indexM < listMembers.size(); indexM++) {
							listMemberFirstName.add(listMembers.get(indexM).getFirstName());
						}
						endPointChildAlert.setFirstNameMembers(listMemberFirstName);
						;
						listEndPointChildAlert.add(endPointChildAlert);
						findOk = true;
					}
				} catch (DaoException e) {
					logger.info("RESPONSE_CHILD_ALERT NO MEDICAL RECORD FOR " + listPerson.get(index).getFirstName()
							+ " " + listPerson.get(index).getLastName());
				}
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_CHILD_ALERT LIST VIDE FOR ADDRESS" + address);
		}
		logger.info("RESPONSE_CHILD_ALERT " + address);
		return listEndPointChildAlert;
	}

	@Override
	public EndPointFireStationAndCount getPersonsByFireStation(int stationNumber) throws DaoException {
		this.stationNumber = stationNumber;

		EndPointFireStationAndCount endPointFireStationAndCount = new EndPointFireStationAndCount();
		List<EndPointFireStation> listEndPointFireStation = new ArrayList<EndPointFireStation>();
		int index;
		int indexP;
		int age = 0;
		int nbAdult = 0;
		int nbChild = 0;
		boolean findOk = false;
		
		List<FireStation> listFireStation = fireStationDao.getAllFireStation();
		List<Person> listPerson = personDao.getAllPerson();
		
		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getStation() == stationNumber) {
				nbChild = 0;
				nbAdult = 0;
				for (indexP = 0; indexP < listPerson.size(); indexP++) {
					if (listPerson.get(indexP).getAddress()
							.contentEquals(listFireStation.get(index).getAddress()) == true) {
						EndPointFireStation endPointFireStation = new EndPointFireStation();
						endPointFireStation.setFirstName(listPerson.get(indexP).getFirstName());
						endPointFireStation.setLastName(listPerson.get(indexP).getLastName());
						endPointFireStation.setPhone(listPerson.get(indexP).getPhone());
						endPointFireStation.setAddress(listPerson.get(indexP).getAddress());
						listEndPointFireStation.add(endPointFireStation);
						findOk = true;
					}

				}
			}
		}
		for (indexP = 0; indexP < listEndPointFireStation.size(); indexP++) {

			try {
				MedicalRecord mr = medicalRecordDao.getMedicalRecord(listEndPointFireStation.get(indexP).getFirstName(),
						listEndPointFireStation.get(indexP).getLastName());
				AgeCalculator ac = new AgeCalculator();
				age = ac.calculateAge(mr.getBirthdate());
				if (age < 18) {
					nbChild++;
				} else {
					nbAdult++;
				}
			} catch (DaoException e) {
				logger.info("RESPONSE_FIRESTATION_AND_COUNT NO MEDICAL RECORD FOR " + listEndPointFireStation.get(indexP).getFirstName() + " "
						+ listEndPointFireStation.get(indexP).getLastName());
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_FIRESTATION_AND_COUNT LIST VIDE FOR STATION " + stationNumber);
		}
		endPointFireStationAndCount.setEndPointFireStation(listEndPointFireStation);
		endPointFireStationAndCount.setNbAdult(nbAdult);
		endPointFireStationAndCount.setNbChild(nbChild);
		logger.info("RESPONSE_FIRESTATION_AND_COUNT " + stationNumber);
		return endPointFireStationAndCount;

	}
}
