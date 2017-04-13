package com.stravaapi.komwatcher;

import java.io.File;
import java.util.HashMap;

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
    	
    	File applicationProperties = new File(Application.class.getClassLoader().getResource("application.properties").getFile());
    	HashMap<String, String> propertyMap = new HashMap<String, String>();
    	propertyMap.put(PropertiesConstants.CLIENT_ID, SystemProperties.getPropertyAsString(PropertiesConstants.CLIENT_ID));
    	propertyMap.put(PropertiesConstants.CLIENT_SECRET, SystemProperties.getPropertyAsString(PropertiesConstants.CLIENT_SECRET));
    	propertyMap.put(PropertiesConstants.AUTHORIZATION_CODE, SystemProperties.getPropertyAsString(PropertiesConstants.AUTHORIZATION_CODE));
    	SystemProperties.setMultiplePropertiesInFile(propertyMap, applicationProperties);
    	
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
    
/*    private CLIOptions getCLIOptions(String[] args) {
    	
    	Options options = new Options();
    	options.addOption("-l", true, "Directory of log files");
    	options.addOption("-s", true, "JSON file with list of subscribers");
    	CommandLineParser commandLineParser = new DefaultParser();
    	CommandLine commandLine = commandLineParser.parse(options, args);
    	commandLine.
    }*/
}
