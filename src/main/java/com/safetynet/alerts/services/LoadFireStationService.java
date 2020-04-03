package com.safetynet.alerts.services;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;

public interface LoadFireStationService {
	
	public void loadData() throws JsonMappingException, IOException, ClassNotFoundException;

}
