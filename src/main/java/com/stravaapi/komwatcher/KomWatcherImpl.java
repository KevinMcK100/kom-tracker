package com.stravaapi.komwatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.stravaapi.komwatcher.models.Subscriber;
import com.stravaapi.komwatcher.models.email.EmailContent;
import com.stravaapi.komwatcher.models.email.EmailType;
import com.stravaapi.komwatcher.models.email.Segment;
import com.stravaapi.komwatcher.utils.FileUtils;

import javastrava.api.v3.model.StravaSegmentEffort;

public class KomWatcherImpl {
	
	final static Logger logger = Logger.getLogger(KomWatcherImpl.class);
	
	private File subscribersFile;
	
	public KomWatcherImpl(File subscribersFile) {
		this.subscribersFile = subscribersFile;
	}

	public void startService() {
		
		List<Subscriber> subscribers = generateSubscriberList();

		for(Subscriber subscriber : subscribers) {
			
			logger.info("Starting KOM comparison for " + subscriber.getAthleteName());
			
			//Retrieve list of current KOMs from Strava
			List<StravaSegmentEffort> currentKoms = getAthleteKoms(subscriber);
			//List<StravaSegmentEffort> currentKoms = null;
			if(logger.isDebugEnabled()) {
				logger.debug("Current list of KOMs:");
				currentKoms.forEach(kom -> logger.debug(kom));
			}
			
			//Attempt to create a new KOM storage file for subscriber (if it already exists it will do nothing)
			//Example koms-123456.json
			String currentDir = System.getProperty("user.dir");
	    	String subscriberKomFile = currentDir + 
	    			SystemProperties.getPropertyAsString(PropertiesConstants.PREVIOUS_KOMS_FOLDER) + 
	    			SystemProperties.getPropertyAsString(PropertiesConstants.PREVIOUS_KOMS_PREFIX) + 
	    			subscriber.getAthleteId() + 
	    			SystemProperties.getPropertyAsString(PropertiesConstants.PREVIOUS_KOMS_SUFFIX);
	    	
	    	File komFile = new File(subscriberKomFile);
	    	//If there are no previous KOMs saved then create new file, save current list of KOMs and terminate execution for this subscriber
	    	try {
				if(komFile.createNewFile()) { //File doesn't exist
					//Write current list of KOMs to new file just created
					logger.info("Subscriber has no list of previous KOMs.");
					logger.info("Saving list of curret KOMs to " + subscriberKomFile);
					FileUtils.writeJsonListToFile(subscriberKomFile, currentKoms);
				} else { //File does exist
					//Retrieve list of KOMs from previous time of checking - Read file operation					
					List<StravaSegmentEffort> existingKoms = FileUtils.readJsonListFromFile(subscriberKomFile, StravaSegmentEffort.class);
					if(logger.isDebugEnabled()) {
						logger.debug("List of pervious KOMs:");
						existingKoms.forEach(kom -> logger.debug(kom));
					}
					
					//Perform comparison
					KomComparator komComparator = new KomComparator(existingKoms, currentKoms);
					List<StravaSegmentEffort> komsGained = komComparator.getKomsGained();
					List<StravaSegmentEffort> komsLost = komComparator.getKomsLost();
			
					if(komsGained.isEmpty() && komsLost.isEmpty()) {
						logger.info("Previous and current KOM lists are identical. Exiting without sending email");
					} else  {						
						
						EmailService emailService = new EmailService();
						
						//Send email for KOMs gained
						if(!komsGained.isEmpty()) {
							//Get URL for every KOM in list
							logger.info("Subscriber has gained the following segments:");
							List<Segment> segmentDetails = getSegmentDetailsForEmail(komsGained);
							EmailContent email = new EmailContent(subscriber.getAthleteName(), segmentDetails, EmailType.KOM_GAINED);
							String renderedHtml = getRenderedHtml(email);
							emailService.sendEmail(SystemProperties.getPropertyAsString(PropertiesConstants.KOM_GAINED_SUBJECT), 
									renderedHtml, subscriber.getEmailAddress());
						}
						//Send email for KOMs lost
						if(!komsLost.isEmpty()) {
							logger.info("Subscriber has lost the following segments:");
							List<Segment> segmentDetails = getSegmentDetailsForEmail(komsLost);
							EmailContent email = new EmailContent(subscriber.getAthleteName(), segmentDetails, EmailType.KOM_LOST);
							String renderedHtml = getRenderedHtml(email);
							emailService.sendEmail(SystemProperties.getPropertyAsString(PropertiesConstants.KOM_LOST_SUBJECT), 
									renderedHtml, subscriber.getEmailAddress());
						}
						//Write new list of current KOMs to file
						logger.info("Writing list of current KOMs to " + subscriberKomFile);
						FileUtils.writeJsonListToFile(subscriberKomFile, currentKoms);
					}
					
				}
			} catch (IOException e) {
				logger.error("An error occured while attempting to create the file " + subscriberKomFile, e);
			}
		}
	}
	
	private List<Subscriber> generateSubscriberList() {
		
		List<Subscriber> subscribers = null;
		try {
			Gson gson = new Gson();
			JsonReader jsonReader = new JsonReader(new FileReader(subscribersFile));
			Type type = new TypeToken<List<Subscriber>>(){}.getType();
			return gson.fromJson(jsonReader, type);
		} catch (FileNotFoundException e) {
			logger.error("Error occured attempting to read the list of subscribers from JSON file. Ensure the file " + subscribersFile + " exists", e);
		}
		return subscribers;
	}

	private List<Segment> getSegmentDetailsForEmail(List<StravaSegmentEffort> koms) {
		
		List<Segment> segmentUrls = new ArrayList<Segment>();
		for(StravaSegmentEffort kom : koms) {
			String segmentId = String.valueOf(kom.getSegment().getId());
			String fullUrl = SystemProperties.getPropertyAsString(PropertiesConstants.SEGMENT_BASE_URL) + segmentId;
			segmentUrls.add(new Segment(kom.getName(), fullUrl));
			logger.info("Segment name: " + kom.getName() + " Segment Id: " + kom.getSegment().getId() + "Segment URL: " + fullUrl);
		}
		
		return segmentUrls;
	}
	
	private String getRenderedHtml(EmailContent email) {
		
		MustacheFactory mustacheFactory = new DefaultMustacheFactory();
		Mustache mustache = mustacheFactory.compile(SystemProperties.getPropertyAsString(PropertiesConstants.MUSTACHE_TEMPLATE));
		StringWriter stringWriter = new StringWriter();
		List<Object> mustacheObjects = new ArrayList<Object>();
		mustacheObjects.add(email);
		String poweredByStravaImage = SystemProperties.getPropertyAsString(PropertiesConstants.POWERED_BY_STRAVA_IMAGE);
		mustacheObjects.add(poweredByStravaImage);
		mustache.execute(stringWriter, mustacheObjects);
		String htmlContent = stringWriter.toString();
		
		return htmlContent;
	}
	
	private List<StravaSegmentEffort> getAthleteKoms(Subscriber subscriber) {
		
		StravaApiClient stravaApiClient = new StravaApiClient();
		List<StravaSegmentEffort> athleteKoms = stravaApiClient.fetchAthleteKomList(subscriber.getAthleteId());
		return athleteKoms;
	}

	public File getSubscribersFile() {
		return subscribersFile;
	}

	public void setSubscribersFile(File subscribersFile) {
		this.subscribersFile = subscribersFile;
	}
}
