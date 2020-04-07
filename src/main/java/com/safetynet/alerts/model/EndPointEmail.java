package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointEmail")
public class EndPointEmail {
	private List<String> listEmail;
	
	public EndPointEmail() {
	}

	public List<String> getListEmail() {
		return listEmail;
	}

	public void setListEmail(List<String> listEmail) {
		this.listEmail = listEmail;
	}
}
