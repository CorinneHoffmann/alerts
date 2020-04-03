package com.safetynet.alerts.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dao.MedicalRecordDao;
import com.safetynet.alerts.exception.ControllerException;
import com.safetynet.alerts.exception.DaoCreationException;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecordForReturnFormat;
import com.safetynet.alerts.services.MedicalRecordService;
import com.sefetynet.alerts.util.ConvertMedicalRecordForJson;

@RestController("medicalRecordController")
@RequestMapping("/medicalrecord")
public class MedicalRecordController {

	Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
	
	@Autowired
	MedicalRecordForReturnFormat medicalRecordForReturnFormat;
	
	@Autowired
	MedicalRecord medicalRecord;
	
	@Autowired
	MedicalRecordDao medicalRecordDao;

	@Autowired
	MedicalRecordService medicalRecordService;
	
	// @GetMapping(value = "/medicalrecord")
	@GetMapping
	List<MedicalRecord> list() {
		return medicalRecordDao.getAllMedicalRecord();
	}

	// @PostMapping(value = "/medicalrecord", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public MedicalRecordForReturnFormat createMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws ParseException, DaoCreationException {
		logger.info("QUERY_CREATE_MEDICALRECORD " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		medicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);

		//converion de la date pour affichage		
		ConvertMedicalRecordForJson convertMedicalRecordForJson = new ConvertMedicalRecordForJson(medicalRecord);
		return convertMedicalRecordForJson.convertDateForPublication();
	}

	// @PutMapping(value = "/medicalrecord", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping
	public MedicalRecordForReturnFormat updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws DaoException, ParseException {
		logger.info("QUERY_UPDATE_MEDICALRECORD " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		medicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
		//converion de la date pour affichage		
		ConvertMedicalRecordForJson convertMedicalRecordForJson = new ConvertMedicalRecordForJson(medicalRecord);
		return convertMedicalRecordForJson.convertDateForPublication();
	}

	@DeleteMapping
	public MedicalRecordForReturnFormat deleteMedicalRecord(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName)
			throws DaoException, ControllerException, ParseException {

		if (firstName.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		if (lastName.isEmpty()) {
			throw new ControllerException("Vous devez saisir les paramètres attendus dans l'URL");
		}
		logger.info("QUERY_DELETE_MEDICALRECORD " + firstName + " " + lastName);
		
		medicalRecord = medicalRecordService.deleteMedicalRecord(firstName, lastName);
		//converion de la date pour affichage		
		ConvertMedicalRecordForJson convertMedicalRecordForJson = new ConvertMedicalRecordForJson(medicalRecord);
		return convertMedicalRecordForJson.convertDateForPublication();

	}
}
