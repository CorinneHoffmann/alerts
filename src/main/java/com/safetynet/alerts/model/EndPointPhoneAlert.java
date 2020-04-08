package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointPhoneAlert")
public class EndPointPhoneAlert {

private List<String> listPhone;
	
	public EndPointPhoneAlert() {
	}

	public List<String> getListPhone() {
		return listPhone;
	}

	public void setListPhone(List<String> listPhone) {
		this.listPhone = listPhone;
	}
}
