package com.stravaapi.komwatcher.config;

import java.util.ResourceBundle;

public class EmailConfig {

	private static String CONFIG_LOCATION = "email";
	private static ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_LOCATION);
	
	public static final String MUSTACHE_TEMPLATE = string("template.mustache");
	
	public static final String HOST_NAME = string("host.name");
	
	public static final Integer SMTP_PORT = integer("smtp.port");
	
	public static final Boolean SSL_ON_CONNECT = bool("ssl_on_connect");
	
	public static final String AUTH_USERNAME = string("authentication.username");
	
	public static final String AUTH_PASSWORD = string("authentication.password");
	
	public static final String SENDER_ADDRESS = string("sender.address");
	
	public static final String SENDER_NAME = string("sender.name");
		
	public static final String POWERED_BY_STRAVA_IMAGE = string("resources.images.powered_by_strava");
	
	public static final String POWERED_BY_STRAVA_IMAGE_URL_LINK = string("resources.images.powered_by_strava.link");
	
	public static final String SEGMENT_BASE_URL = string("segment.base_url");
	
	public static final String KOM_GAINED_SUBJECT = string("subject.kom_gained");
	
	public static final String KOM_LOST_SUBJECT = string("subject.kom_lost");
	
	public static final String KOM_GAINED_BODY = string("body.kom_gained");
	
	public static final String KOM_LOST_BODY = string("body.kom_lost");

	public static Integer integer(String property) {
		return Integer.parseInt(bundle.getString(property));
	}
	
	public static String string(String property) {
		return bundle.getString(property);
	}
	
	public static Boolean bool(String property) {
		return Boolean.parseBoolean(bundle.getString(property));
	}
}
