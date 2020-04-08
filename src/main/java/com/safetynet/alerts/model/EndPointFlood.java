package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("endPointFlood")
public class EndPointFlood {
	private List<EndPointFireAndNumberStation> endPointFireAndNumberStation;

	public List<EndPointFireAndNumberStation> getEndPointFireAndNumberStation() {
		return endPointFireAndNumberStation;
	}

	public void setEndPointFireAndNumberStation(List<EndPointFireAndNumberStation> endPointFireAndNumberStation) {
		this.endPointFireAndNumberStation = endPointFireAndNumberStation;
	}
}
