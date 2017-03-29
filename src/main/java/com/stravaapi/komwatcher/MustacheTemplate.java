package com.stravaapi.komwatcher;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;

public class MustacheTemplate {

	private List<StravaSegmentEffort> komLost;
	
	public MustacheTemplate(List<StravaSegmentEffort> komLost) {
		this.komLost=  komLost;
	}
	
	public String getKomGainedTemplate() {
		return "";
	}
	
	public String getKomLostTemplate() {
		return "";
	}

	public List<StravaSegmentEffort> getKomLost() {
		return komLost;
	}

	public void setKomLost(List<StravaSegmentEffort> komLost) {
		this.komLost = komLost;
	}
}
