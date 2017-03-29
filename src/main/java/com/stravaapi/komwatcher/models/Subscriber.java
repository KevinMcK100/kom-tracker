package com.stravaapi.komwatcher.models;

public class Subscriber {

	private Integer athleteId;
	private String athleteName;
	private String emailAddress;

	public Subscriber(Integer athleteId, String athleteName, String emailAddress) {
		this.athleteId = athleteId;
		this.athleteName = athleteName;
		this.emailAddress = emailAddress;
	}

	public Integer getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(Integer athleteId) {
		this.athleteId = athleteId;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
