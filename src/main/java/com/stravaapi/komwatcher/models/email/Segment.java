package com.stravaapi.komwatcher.models.email;

public class Segment {

	private String segmentName;
	private String segmentUrl;
	
	public Segment(String segmentName, String segmentUrl) {
		this.segmentName = segmentName;
		this.segmentUrl = segmentUrl;
	}
	
	public String getSegmentName() {
		return segmentName;
	}
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}
	public String getSegmentUrl() {
		return segmentUrl;
	}
	public void setSegmentUrl(String segmentUrl) {
		this.segmentUrl = segmentUrl;
	}
	
	
}
