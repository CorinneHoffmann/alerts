package com.safetynet.alerts.services;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;

public interface MedicalRecordService {
	
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws DaoCreationException;
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws DaoException;
	
	public MedicalRecord deleteMedicalRecord(String firstName, String lastName) throws DaoException;

}
