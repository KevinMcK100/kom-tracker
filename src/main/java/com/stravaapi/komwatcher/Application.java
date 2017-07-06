package com.stravaapi.komwatcher;

import java.io.File;

import org.apache.log4j.Logger;

public class Application 
{
	final static Logger logger = Logger.getLogger(Application.class);

	public static void main( String[] args )
    {  	
    	String rootDir = System.getProperty("user.dir");
    	String configDir = rootDir + "/config/kom-tracker.conf";
    	File configFile = new File(configDir);
    	SystemProperties.setPropertiesFromFile(configFile);
    	
    	String subscribersPath = SystemProperties.getPropertyAsString("subscribers.file.path");
    	File subscribersRelative = new File(rootDir + subscribersPath);
    	File subscribersAbsolute = new File(subscribersPath);
    	File subscribersFile;
    	
    	logger.info("Starting KOM Tracker service"); 
    	if(subscribersRelative.exists()) {
    		logger.debug("Subscribers file found in relative path " + subscribersRelative);
    		subscribersFile = subscribersRelative;
    	} else if(subscribersAbsolute.exists()) {
    		logger.debug("Subscribers file found in absolute path " + subscribersAbsolute);
    		subscribersFile = subscribersAbsolute;
    	} else {
    		logger.error("The path " + subscribersPath + " spcified as the location of the service subscribers"
    				+ " file cannot be found in the context of a relative or absolute path. Please check your config.");
    		return;
    	}
    	
    	KomWatcherImpl komWatcherImpl = new KomWatcherImpl(subscribersFile);
    	komWatcherImpl.startService();
    	logger.info("Exiting KOM Tracker service");

    }
}
