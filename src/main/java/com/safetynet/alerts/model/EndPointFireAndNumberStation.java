package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointFireAndNumberStation")
public class EndPointFireAndNumberStation {

	private int station;
	private List<EndPointFireDetail> endPointFireList;
	
	
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public List<EndPointFireDetail> getEndPointFireList() {
		return endPointFireList;
	}
	public void setEndPointFireList(List<EndPointFireDetail> endPointFireList) {
		this.endPointFireList = endPointFireList;
	}
	
}
