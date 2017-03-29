package com.stravaapi.komwatcher.config;

import java.util.ResourceBundle;

public class GeneralConfig {

	// Read properties in as ResourceBundle
	private static final String CONFIG_LOCATION = "application";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_LOCATION);
	
	// Properties
	public static final Integer CLIENT_ID = integer("strava.client_id");
	
	public static final String CLIENT_SECRET = string("strava.client_secret");
	
	public static final String AUTHORIZATION_CODE = string("strava.authorization_code");
	
	public static final String PREVIOUS_KOMS_FOLDER = string("io.previous_koms.folder");
	
	public static final String PREVIOUS_KOMS_PREFIX = string("io.previous_koms.prefix");
	
	public static final String PREVIOUS_KOMS_SUFFIX = string("io.previous_koms.suffix");
	
	public static Integer integer(String property) {
		return Integer.parseInt(bundle.getString(property));
	}
	
	public static String string(String property) {
		return bundle.getString(property);
	}
	
	
}
