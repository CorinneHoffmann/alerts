package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointChildAlert")
public class EndPointChildAlert {

	List<EndPointChildAlertDetail> endPointChildAlertList;

	public List<EndPointChildAlertDetail> getEndPointChildAlertList() {
		return endPointChildAlertList;
	}

	public void setEndPointChildAlertList(List<EndPointChildAlertDetail> endPointChildAlertList) {
		this.endPointChildAlertList = endPointChildAlertList;
	}
	
	
}
