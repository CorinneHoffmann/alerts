package com.sefetynet.alerts.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordForReturnFormat;

public class ConvertMedicalRecordForJson {

	@Autowired
	MedicalRecord medicalRecord;

	public ConvertMedicalRecordForJson(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public MedicalRecordForReturnFormat convertDateForPublication() throws java.text.ParseException {

		MedicalRecordForReturnFormat medicalRecordForReturnFormat = new MedicalRecordForReturnFormat();

		medicalRecordForReturnFormat.setFirstName(medicalRecord.getFirstName());
		medicalRecordForReturnFormat.setLastName(medicalRecord.getLastName());
		medicalRecordForReturnFormat.setMedications(medicalRecord.getMedications());
		medicalRecordForReturnFormat.setAllergies(medicalRecord.getAllergies());
		medicalRecordForReturnFormat.setBirthdate(convertDate(medicalRecord.getBirthdate()));

		return medicalRecordForReturnFormat;
	}

	public String convertDate(Date date) throws java.text.ParseException {

		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		String stringDate = dateFormat.format(date);
		return stringDate;
	}
}
