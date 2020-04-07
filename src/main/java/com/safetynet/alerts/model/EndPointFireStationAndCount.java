package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointFireStationAndCount")
public class EndPointFireStationAndCount {
	
	private List<EndPointFireStation> endPointFireStation;
	private int nbAdult;
	private int nbChild;
	
	public List<EndPointFireStation> getEndPointFireStation() {
		return endPointFireStation;
	}
	public void setEndPointFireStation(List<EndPointFireStation> endPointFireStation) {
		this.endPointFireStation = endPointFireStation;
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
