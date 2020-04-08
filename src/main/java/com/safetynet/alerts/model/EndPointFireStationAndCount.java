package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointFireStationAndCount")
public class EndPointFireStationAndCount {
	
	private List<EndPointFireStationDetail> endPointFireStationList;
	private int nbAdult;
	private int nbChild;
	
	
	public List<EndPointFireStationDetail> getEndPointFireStationList() {
		return endPointFireStationList;
	}
	public void setEndPointFireStationList(List<EndPointFireStationDetail> endPointFireStationList) {
		this.endPointFireStationList = endPointFireStationList;
	}
	public int getNbAdult() {
		return nbAdult;
	}
	public void setNbAdult(int nbAdult) {
		this.nbAdult = nbAdult;
	}
	public int getNbChild() {
		return nbChild;
	}
	public void setNbChild(int nbChild) {
		this.nbChild = nbChild;
	}
}
