package com.stravaapi.komwatcher;

import org.apache.log4j.Logger;

public class Application 
{
	
	final static Logger logger = Logger.getLogger(Application.class);
	
    public static void main( String[] args )
    {  	
    	logger.info("Starting KOM Tracker service");
    	
    	//Check that a JSON file containing service subscribers has been passed as argument
    	if(args.length != 1) {
    		logger.error("No argument passed. You must pass a JSON file with all subscribers "
    				+ "to the service and pass that JSON file as an argument to this program");
    	} else {
    		//Continue normal program execution
    		KomWatcherImpl komWatcherImpl = new KomWatcherImpl(args[0]);
    		komWatcherImpl.startService();
    		
    	}
    	
    	logger.info("Exiting KOM Tracker service");
    }
}
