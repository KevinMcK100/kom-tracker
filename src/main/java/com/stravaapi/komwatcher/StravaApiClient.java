package com.stravaapi.komwatcher;

import java.util.List;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.Strava;

public class StravaApiClient {
	
	private Strava strava;
	
	public StravaApiClient() {
		
		Token token = authenticateApiUser();
		strava = new Strava(token);
	}
	
	public List<StravaSegmentEffort> fetchAthleteKomList(Integer athleteId) {
		
		List<StravaSegmentEffort> komList = strava.listAllAthleteKOMs(athleteId);
		return komList;
	}
		
	private Token authenticateApiUser() {
		
		Token token = new Token();
		AuthorisationService service = new AuthorisationServiceImpl();
		token = service.tokenExchange(SystemProperties.getPropertyAsInteger(PropertiesConstants.CLIENT_ID), 
				SystemProperties.getPropertyAsString(PropertiesConstants.CLIENT_SECRET), 
				SystemProperties.getPropertyAsString(PropertiesConstants.AUTHORIZATION_CODE));
		
		return token;
	}

	public Strava getStrava() {
		return strava;
	}

	public void setStrava(Strava strava) {
		this.strava = strava;
	}
}
