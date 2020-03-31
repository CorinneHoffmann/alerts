package com.safetynet.alerts;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.alerts.services.LoadPersonService;
import com.safetynet.alerts.services.LoadPersonServiceImpl;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) throws JsonMappingException, IOException {

		SpringApplication.run(AlertsApplication.class, args);

		System.out.println("Alerts");

	}
}
