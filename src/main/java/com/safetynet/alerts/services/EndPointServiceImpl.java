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
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.EndPointChildAlert;
import com.safetynet.alerts.model.EndPointChildAlertDetail;
import com.safetynet.alerts.model.EndPointEmail;
import com.safetynet.alerts.model.EndPointFireAndNumberStation;
import com.safetynet.alerts.model.EndPointFireDetail;
import com.safetynet.alerts.model.EndPointFireStationAndCount;
import com.safetynet.alerts.model.EndPointFireStationDetail;
import com.safetynet.alerts.model.EndPointFlood;
import com.safetynet.alerts.model.EndPointPersonInfo;
import com.safetynet.alerts.model.EndPointPersonInfoDetail;
import com.safetynet.alerts.model.EndPointPhoneAlert;
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

	@Autowired
	EndPointPhoneAlert endPointPhoneAlert;

	@Autowired
	EndPointFireStationAndCount endPointFireStationAndCount;

	@Autowired
	EndPointFireAndNumberStation endPointFireAndNumberStation;

	@Autowired
	EndPointFlood endPointFlood;

	@Autowired
	EndPointPersonInfo endPointPersonInfo;

	@Autowired
	EndPointChildAlert endPointChildAlert;

	Logger logger = LoggerFactory.getLogger(EndPointServiceImpl.class);

	private String city;
	private String address;
	private int stationNumber;
	List<Integer> stations;
	private String firstName;
	private String lastName;

	/*
	 * @param city
	 * 		String city for select in list of person
	 * @return EndPointEmail
	 * 		object EndPointEmail - email list of the selected persons
	 */
	@Override
	public EndPointEmail getEmailsByCity(String city) {
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
			logger.info("RESPONSE_END_POINT_EMAIL LIST EMPTY FOR PARAMETER " + city);
		} else {
			logger.info("RESPONSE_END_POINT_EMAIL " + city);
		}
		List<String> listEmail = setEmail.stream().collect(Collectors.toList());
		endPointEmail.setListEmail(listEmail);
		return endPointEmail;

	}
	
	/*
	 * @param address
	 * 		String address for select in list of person
	 * @return EndPointChildAlert
	 * 		object EndPointChildAlert - child list who live in this address and other persons who live in this address
	 */
	@Override
	public EndPointChildAlert getChildAlert(String address) throws DaoException {
		this.address = address;
		List<EndPointChildAlertDetail> listEndPointChildAlert = new ArrayList<EndPointChildAlertDetail>();

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
						EndPointChildAlertDetail endPointChildAlertDetail = new EndPointChildAlertDetail();
						endPointChildAlertDetail.setFirstName(listPerson.get(index).getFirstName());
						endPointChildAlertDetail.setLastName(listPerson.get(index).getLastName());
						endPointChildAlertDetail.setAge(age);
						listMembers = personDao.GetFamilly(address, listPerson.get(index).getFirstName());
						List<String> listMemberFirstName = new ArrayList<String>();
						for (indexM = 0; indexM < listMembers.size(); indexM++) {
							listMemberFirstName.add(listMembers.get(indexM).getFirstName());
						}
						endPointChildAlertDetail.setFirstNameMembers(listMemberFirstName);
						;
						listEndPointChildAlert.add(endPointChildAlertDetail);
						findOk = true;
					}
				} catch (DaoException e) {
					logger.info("RESPONSE_CHILD_ALERT NO MEDICAL RECORD FOR " + listPerson.get(index).getFirstName()
							+ " " + listPerson.get(index).getLastName());
				}
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_CHILD_ALERT LIST EMPTY FOR PARAMETER " + address);
		} else {
			logger.info("RESPONSE_CHILD_ALERT " + address);
		}
		endPointChildAlert.setEndPointChildAlertList(listEndPointChildAlert);
		return endPointChildAlert;
	}

	/*
	 * @param stationNumber
	 * 		String stationNumber for select in list of firestation
	 * @return EndPointFireStationAndCount
	 * 		object EndPointFireStationAndCount - persons list who live in addresses covered by the firestation number and number of child and number of adults
	 */
	@Override
	public EndPointFireStationAndCount getPersonsByFireStation(int stationNumber) throws DaoException {
		this.stationNumber = stationNumber;

		// EndPointFireStationAndCount endPointFireStationAndCount = new
		// EndPointFireStationAndCount();
		List<EndPointFireStationDetail> listEndPointFireStation = new ArrayList<EndPointFireStationDetail>();
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
						EndPointFireStationDetail endPointFireStationDetail = new EndPointFireStationDetail();
						endPointFireStationDetail.setFirstName(listPerson.get(indexP).getFirstName());
						endPointFireStationDetail.setLastName(listPerson.get(indexP).getLastName());
						endPointFireStationDetail.setPhone(listPerson.get(indexP).getPhone());
						endPointFireStationDetail.setAddress(listPerson.get(indexP).getAddress());
						listEndPointFireStation.add(endPointFireStationDetail);
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
				logger.info("RESPONSE_FIRESTATION_AND_COUNT NO MEDICAL RECORD FOR "
						+ listEndPointFireStation.get(indexP).getFirstName() + " "
						+ listEndPointFireStation.get(indexP).getLastName());
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_FIRESTATION_AND_COUNT LIST EMPTY FOR PARAMETER " + stationNumber);
		} else {
			logger.info("RESPONSE_FIRESTATION_AND_COUNT " + stationNumber);
		}
		endPointFireStationAndCount.setEndPointFireStationList(listEndPointFireStation);
		endPointFireStationAndCount.setNbAdult(nbAdult);
		endPointFireStationAndCount.setNbChild(nbChild);
		return endPointFireStationAndCount;
	}

	/*
	 * @param firestation
	 * 		firestation number for select in list of firestation
	 * @return EndPointPhoneAlert
	 * 		object EndPointPhoneAlert - phone list of persons who live in addresses covered by firestation
	 */
	@Override
	public EndPointPhoneAlert getPhoneByStation(int stationNumber) {

		this.stationNumber = stationNumber;
		int index;
		int indexP;
		boolean findOk = false;

		List<FireStation> listFireStation = fireStationDao.getAllFireStation();
		List<Person> listPerson = personDao.getAllPerson();
		Set<String> setPhone = new HashSet<>();

		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getStation() == stationNumber) {
				for (indexP = 0; indexP < listPerson.size(); indexP++) {
					if (listPerson.get(indexP).getAddress()
							.contentEquals(listFireStation.get(index).getAddress()) == true) {
						setPhone.add(listPerson.get(indexP).getPhone());
						findOk = true;
					}
				}
			}
		}
		if (findOk == false) {
			logger.error("RESPONSE_END_POINT_PHONE_ALERT LIST EMPTY FOR PARAMETER " + stationNumber);
		} else {
			logger.info("RESPONSE_END_POINT_PHONE_ALERT " + stationNumber);
		}
		List<String> listPhone = setPhone.stream().collect(Collectors.toList());
		endPointPhoneAlert.setListPhone(listPhone);
		return endPointPhoneAlert;

	}

	/*
	 * @param address
	 * 		String address for select in list of firestation
	 * @return EndPointFireAndNumberStation
	 * 		object EndPointFireAndNumberStation - persons list who live in addresses covered by firestation and firestation number
	 */
	@Override
	public EndPointFireAndNumberStation getFireByAddress(String address) throws DaoException {
		this.address = address;
		List<EndPointFireDetail> listEndPointFire = new ArrayList<EndPointFireDetail>();
		int index;
		boolean findOk = false;
		int age = 0;
		int station = 0;

		List<Person> listPerson = personDao.getAllPerson();
		List<FireStation> listFireStation = fireStationDao.getAllFireStation();

		for (index = 0; index < listPerson.size(); index++) {
			if (listPerson.get(index).getAddress().contentEquals(address) == true) {

				EndPointFireDetail endPointFire = new EndPointFireDetail();
				endPointFire.setFirstName(listPerson.get(index).getFirstName());
				endPointFire.setLastName(listPerson.get(index).getLastName());
				endPointFire.setPhone(listPerson.get(index).getPhone());
				findOk = true;
				try {
					MedicalRecord mr = medicalRecordDao.getMedicalRecord(listPerson.get(index).getFirstName(),
							listPerson.get(index).getLastName());
					AgeCalculator ac = new AgeCalculator();
					age = ac.calculateAge(mr.getBirthdate());
					endPointFire.setAge(age);
					endPointFire.setAllergies(mr.getAllergies());
					endPointFire.setMedications(mr.getMedications());
				} catch (DaoException e) {
					logger.info("RESPONSE_FIRE_BY_ADDRESS NO MEDICAL RECORD FOR " + listPerson.get(index).getFirstName()
							+ " " + listPerson.get(index).getLastName());
				}

				listEndPointFire.add(endPointFire);
			}
		}
		for (index = 0; index < listFireStation.size(); index++) {
			if (listFireStation.get(index).getAddress().contentEquals(address) == true) {
				station = listFireStation.get(index).getStation();
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_FIRE_AND_NUMBER_STATION LIST EMPTY FOR PARAMETER " + address);
		} else {
			logger.info("RESPONSE_FIRE_AND_NUMBER_STATION " + stationNumber);
		}
		endPointFireAndNumberStation.setEndPointFireList(listEndPointFire);
		endPointFireAndNumberStation.setStation(station);
		return endPointFireAndNumberStation;
	}

	/*
	 * @param stations
	 * 		list of stations numbers for select in list of firestation
	 * @return EndPointFlood
	 * 		object EndPointFlood - persons list who live in addresses covered by firestation
	 */
	@Override
	public EndPointFlood getFlood(List<Integer> stations) {
		this.stations = stations;
		List<EndPointFireAndNumberStation> listEndPointFireAndNumberStation = new ArrayList<EndPointFireAndNumberStation>();
		int index;
		int indexF;
		int indexP;
		boolean findOk = false;
		int age = 0;

		List<Person> listPerson = personDao.getAllPerson();
		List<FireStation> listFireStation = fireStationDao.getAllFireStation();

		// boucle sur les stations en parametre
		for (index = 0; index < stations.size(); index++) {
			findOk = false;
			List<EndPointFireDetail> listEndPointFire = new ArrayList<EndPointFireDetail>();
			// recherche dans la liste des firestation des lignes correspondant a la station
			for (indexF = 0; indexF < listFireStation.size(); indexF++) {
				if (listFireStation.get(indexF).getStation() == stations.get(index)) {
					// recherche des personnes correspondant a adresse
					for (indexP = 0; indexP < listPerson.size(); indexP++) {

						if (listPerson.get(indexP).getAddress()
								.contentEquals(listFireStation.get(indexF).getAddress()) == true) {

							EndPointFireDetail endPointFire = new EndPointFireDetail();
							endPointFire.setFirstName(listPerson.get(indexP).getFirstName());
							endPointFire.setLastName(listPerson.get(indexP).getLastName());
							endPointFire.setPhone(listPerson.get(indexP).getPhone());
							findOk = true;

							try {
								MedicalRecord mr = medicalRecordDao.getMedicalRecord(
										listPerson.get(indexP).getFirstName(), listPerson.get(indexP).getLastName());
								AgeCalculator ac = new AgeCalculator();
								age = ac.calculateAge(mr.getBirthdate());
								endPointFire.setAge(age);
								endPointFire.setAllergies(mr.getAllergies());
								endPointFire.setMedications(mr.getMedications());
							} catch (DaoException e) {
								logger.info(
										"RESPONSE_FLOOD NO MEDICAL RECORD FOR " + listPerson.get(indexP).getFirstName()
												+ " " + listPerson.get(indexP).getLastName());
							}

							// chargement de la personne dans la liste des personnes
							listEndPointFire.add(endPointFire);
						}
					}
					// on a traite une adresse de la station
				}
			}
			// fin de la boucle de recherche dans la liste des firestations. Si on a au
			// moins une personne
			// on ecrit EndPointFireAndNumberStation et on ajoute a la liste qui sera
			// retournée
			if (findOk == true) {
				EndPointFireAndNumberStation endPointFireAndNumberStation = new EndPointFireAndNumberStation();
				endPointFireAndNumberStation.setEndPointFireList(listEndPointFire);
				endPointFireAndNumberStation.setStation(stations.get(index));
				listEndPointFireAndNumberStation.add(endPointFireAndNumberStation);
			}
			// on traite la station suivante si plusieurs stations en parametre
		}
		if (listEndPointFireAndNumberStation.size() == 0) {
			logger.info("RESPONSE_FLOOD_LIST_EMPTY FOR PARAMETERS");
		} else {
			logger.info("RESPONSE_FLOOD ");
		}
		endPointFlood.setEndPointFireAndNumberStation(listEndPointFireAndNumberStation);
		return endPointFlood;
	}

	/*
	 * @param firstName and lastName
	 * 		lastName is required
	 * @return EndPointPersonInfo
	 * 		object EndPointPersonInfo - persons list with firstName and lastName
	 */
	@Override
	public EndPointPersonInfo getPersonInfo(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		int index;
		int age = 0;
		boolean findOk = false;
		List<EndPointPersonInfoDetail> listEndPointPersonInfoDetail = new ArrayList<EndPointPersonInfoDetail>();

		List<Person> listPerson = personDao.getAllPerson();

		for (index = 0; index < listPerson.size(); index++) {
			if (((listPerson.get(index).getLastName().contentEquals(lastName) == true)
					&& (listPerson.get(index).getFirstName().contentEquals(firstName) == true))
					|| ((listPerson.get(index).getLastName().contentEquals(lastName) == true)
							&& (firstName.isEmpty()))) {

				EndPointPersonInfoDetail endPointPersonInfoDetail = new EndPointPersonInfoDetail();
				endPointPersonInfoDetail.setLastName(lastName);
				endPointPersonInfoDetail.setAddress(listPerson.get(index).getAddress());
				endPointPersonInfoDetail.setEmail(listPerson.get(index).getEmail());
				findOk = true;
				try {
					MedicalRecord mr = medicalRecordDao.getMedicalRecord(listPerson.get(index).getFirstName(),
							listPerson.get(index).getLastName());
					AgeCalculator ac = new AgeCalculator();
					age = ac.calculateAge(mr.getBirthdate());
					endPointPersonInfoDetail.setAge(age);
					endPointPersonInfoDetail.setAllergies(mr.getAllergies());
					endPointPersonInfoDetail.setMedications(mr.getMedications());
				} catch (DaoException e) {
					logger.info("RESPONSE_PERSON_INFO NO MEDICAL RECORD FOR " + listPerson.get(index).getFirstName()
							+ " " + listPerson.get(index).getLastName());
				}

				listEndPointPersonInfoDetail.add(endPointPersonInfoDetail);
			}
		}
		if (findOk == false) {
			logger.info("RESPONSE_PERSON_INFO LIST EMPTY FOR PARAMETERS " + firstName + " " + lastName);
		} else {
			logger.info("RESPONSE_PERSON_INFO " + firstName + " " + lastName);
		}
		endPointPersonInfo.setEndPointPersonInfoList(listEndPointPersonInfoDetail);
		return endPointPersonInfo;
	}

}
