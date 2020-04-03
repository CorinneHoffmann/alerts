package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;

public interface MedicalRecordDao {
	
	public List<MedicalRecord> getAllMedicalRecord();

	public void setAllMedicalRecord(List<MedicalRecord> medicalRecord);
	
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws DaoCreationException;
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws DaoException;
	
	public MedicalRecord deleteMedicalRecord(String firstName, String lastName) throws DaoException;
	
	public MedicalRecord getMedicalRecord(String firstName, String lastName) throws DaoException;
	
	public void deleteAllMedicalRecord();
	
}
