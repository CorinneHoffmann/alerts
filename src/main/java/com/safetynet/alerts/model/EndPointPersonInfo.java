package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointPersonInfo")
public class EndPointPersonInfo {

	private List<EndPointPersonInfoDetail> endPointPersonInfoList;

	public List<EndPointPersonInfoDetail> getEndPointPersonInfoList() {
		return endPointPersonInfoList;
	}

	public void setEndPointPersonInfoList(List<EndPointPersonInfoDetail> endPointPersonInfoList) {
		this.endPointPersonInfoList = endPointPersonInfoList;
	}
	
}
