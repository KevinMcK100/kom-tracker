package com.stravaapi.komwatcher.models.email;

import java.util.List;

import com.stravaapi.komwatcher.config.EmailConfig;

public class EmailContent {
	
	private String subscriberName;
	private List<Segment> segments;
	private EmailType emailType;
	private int segmentCount;
	private boolean isSingleKom;
	private final String poweredByStravaImage;
	private final String poweredByStravaImageUrlLink;
	private final String mainEmailBody;
	
	public EmailContent(String subscriberName, List<Segment> segments, EmailType emailType) {
		this.subscriberName = subscriberName;
		this.segments = segments;
		this.emailType = emailType;
		this.segmentCount = segments.size();
		this.isSingleKom = (segmentCount == 1) ? true : false;
		this.poweredByStravaImage = EmailConfig.POWERED_BY_STRAVA_IMAGE;
		this.poweredByStravaImageUrlLink = EmailConfig.POWERED_BY_STRAVA_IMAGE_URL_LINK;
		this.mainEmailBody = (emailType.equals(EmailType.KOM_GAINED)) ? EmailConfig.KOM_GAINED_BODY : EmailConfig.KOM_LOST_BODY;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public int getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount) {
		this.segmentCount = segmentCount;
	}

	public boolean isSingleKom() {
		return isSingleKom;
	}

	public void setSingleKom(boolean isSingleKom) {
		this.isSingleKom = isSingleKom;
	}

	public String getPoweredByStravaImage() {
		return poweredByStravaImage;
	}

	public String getPoweredByStravaImageUrlLink() {
		return poweredByStravaImageUrlLink;
	}

	public String getMainEmailBody() {
		return mainEmailBody;
	}

}
