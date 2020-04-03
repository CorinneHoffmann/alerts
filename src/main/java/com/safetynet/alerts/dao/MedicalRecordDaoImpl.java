package com.safetynet.alerts.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;

@Repository("medicalRecordDao")
public class MedicalRecordDaoImpl implements MedicalRecordDao {

	@Autowired
	MedicalRecord medicalRecord;

	List<MedicalRecord> listMedicalRecord;

	Logger logger = LoggerFactory.getLogger(MedicalRecordDaoImpl.class);

	private String firstName;
	private String lastName;

	@Override
	public List<MedicalRecord> getAllMedicalRecord() {
		return listMedicalRecord;
	}

	@Override
	public void setAllMedicalRecord(List<MedicalRecord> listMedicalRecord) {
		this.listMedicalRecord = listMedicalRecord;
	}

	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws DaoCreationException {
		this.medicalRecord = medicalRecord;
		int index;
		boolean findOK = false;		
	
		for (index = 0; index < listMedicalRecord.size(); index++) {
			if ((listMedicalRecord.get(index).getFirstName().contentEquals(medicalRecord.getFirstName()) == true)
					&& (listMedicalRecord.get(index).getLastName().contentEquals(medicalRecord.getLastName()) == true)) {
				findOK = true;
			}
		}
		if (findOK == true) {
			logger.error("RESPONSE_MEDICALRECORD_ALREADY_EXISTS " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			throw new DaoCreationException("La person que vous voulez creer existe deja");
		}	
		logger.info("RESPONSE_MEDICALRECORD_CREATED " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		listMedicalRecord.add(medicalRecord);
		return medicalRecord;
	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws DaoException {
		this.medicalRecord = medicalRecord;
		int index;
		boolean findOK = false;

		for (index = 0; index < listMedicalRecord.size(); index++) {
			if ((listMedicalRecord.get(index).getFirstName().contentEquals(medicalRecord.getFirstName()) == true)
					&& (listMedicalRecord.get(index).getLastName()
							.contentEquals(medicalRecord.getLastName()) == true)) {
				listMedicalRecord.set(index, medicalRecord);
				medicalRecord = listMedicalRecord.get(index);
				findOK = true;
			}
		}
		if (findOK == false) {
			logger.error("RESPONSE_MEDICALRECORD_NOT_FOUND_FOR_UPDATE " + medicalRecord.getFirstName() + " "
					+ medicalRecord.getLastName());
			throw new DaoException("Le dossier medical que vous voulez modifier n\'est pas dans la liste");
		}
		logger.info("RESPONSE_MEDICALRECORD_UPDATED " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return medicalRecord;
	}

	@Override
	public MedicalRecord deleteMedicalRecord(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;

		int index;
		boolean findOK = false;

		for (index = 0; index < listMedicalRecord.size(); index++) {
			if ((listMedicalRecord.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listMedicalRecord.get(index).getLastName()
							.contentEquals(lastName) == true)) {
				medicalRecord = listMedicalRecord.get(index);
				listMedicalRecord.remove(index);
				findOK = true;
				index--;
			}
		}
		if (findOK == false) {
			logger.error("RESPONSE_MEDICALRECORD_NOT_FOUND_FOR_DELETE " + firstName + " " + lastName);
			throw new DaoException("Le dossier medical que vous voulez modifier n\'est pas dans la liste");
		}
		logger.info("RESPONSE_MEDICALRECORD_DELETED " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return medicalRecord;
		
	}


	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;

		int index=0;
		boolean findOK = false;

		while ((index < listMedicalRecord.size()) && (findOK == false)) {
			if ((listMedicalRecord.get(index).getFirstName().contentEquals(firstName) == true)
					&& (listMedicalRecord.get(index).getLastName().contentEquals(lastName) == true)) {
				findOK = true;
				medicalRecord = listMedicalRecord.get(index);
			} else {
				index++;
			}
		}

		if (findOK == false) {
			throw new DaoException("Le dossier medical que vous voulez récupérer n\'est pas dans la liste");
		}
		return medicalRecord;
	}

	@Override
	public void deleteAllMedicalRecord() {
		listMedicalRecord.clear();
	}

}
