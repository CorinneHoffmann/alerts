package com.safetynet.alerts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;

@Service("medicalRecordService")
public class MedicalRecordServiceImpl implements  MedicalRecordService {

	@Autowired
	MedicalRecord medicalRecord;

	@Autowired
	MedicalRecordDao medicalRecordDao;

	private String firstName;

	private String lastName;
	
	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws DaoCreationException {
		this.medicalRecord = medicalRecord;
		medicalRecord = medicalRecordDao.createMedicalRecord(medicalRecord);
		return medicalRecord;
	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws DaoException {
		this.medicalRecord = medicalRecord;
		medicalRecord = medicalRecordDao.updateMedicalRecord(medicalRecord);
		return medicalRecord;
	}

	@Override
	public MedicalRecord deleteMedicalRecord(String firstName, String lastName) throws DaoException {
		this.firstName = firstName;
		this.lastName = lastName;
		medicalRecord = medicalRecordDao.deleteMedicalRecord(firstName,lastName);
		return medicalRecord;
	}

}
