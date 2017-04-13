package com.stravaapi.komwatcher;

public class PropertiesConstants {

	// General application properties constants
	public static final String CLIENT_ID = "strava.client_id";
	public static final String CLIENT_SECRET = "strava.client_secret";
	public static final String AUTHORIZATION_CODE = "strava.authorization_code";	
	public static final String PREVIOUS_KOMS_FOLDER = "io.previous_koms.folder";	
	public static final String PREVIOUS_KOMS_PREFIX = "io.previous_koms.prefix";	
	public static final String PREVIOUS_KOMS_SUFFIX = "io.previous_koms.suffix";
	
	// Email properties constants
	public static final String MUSTACHE_TEMPLATE = "email.template.mustache";
	public static final String HOST_NAME = "email.host.name";
	public static final String SMTP_PORT = "email.smtp.port";
	public static final String SSL_ON_CONNECT = "email.ssl_on_connect";
	public static final String AUTH_USERNAME = "email.authentication.username";
	public static final String AUTH_PASSWORD = "email.authentication.password";
	public static final String SENDER_ADDRESS = "email.sender.address";
	public static final String SENDER_NAME = "email.sender.name";	
	public static final String POWERED_BY_STRAVA_IMAGE = "email.resources.images.powered_by_strava";
	public static final String POWERED_BY_STRAVA_IMAGE_URL_LINK = "email.resources.images.powered_by_strava.link";
	public static final String SEGMENT_BASE_URL = "email.segment.base_url";
	public static final String KOM_GAINED_SUBJECT = "email.subject.kom_gained";
	public static final String KOM_LOST_SUBJECT = "email.subject.kom_lost";
	public static final String KOM_GAINED_BODY = "email.body.kom_gained";
	public static final String KOM_LOST_BODY = "email.body.kom_lost";
}
