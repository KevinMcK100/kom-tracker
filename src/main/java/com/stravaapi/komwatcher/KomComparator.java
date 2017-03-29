package com.stravaapi.komwatcher;

import java.util.ArrayList;
import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;

public class KomComparator {

	private List<StravaSegmentEffort> existingKoms;
	private List<StravaSegmentEffort> currentKoms;
	
	public KomComparator(List<StravaSegmentEffort> existingKoms, List<StravaSegmentEffort> currentKoms) {
		this.existingKoms = existingKoms;
		this.currentKoms = currentKoms;
	}
	
	public List<StravaSegmentEffort> getKomsLost() {
		
		return compareKoms(existingKoms, currentKoms);
	}
	
	public List<StravaSegmentEffort> getKomsGained() {
		
		return compareKoms(currentKoms, existingKoms);
	}
	
	/**
	 * Returns KOMs which are in the base list but not in the secondary list
	 */
	private List<StravaSegmentEffort> compareKoms(List<StravaSegmentEffort> baseList, List<StravaSegmentEffort> secondaryList) {
		
		List<StravaSegmentEffort> diffList = new ArrayList<StravaSegmentEffort>();
		
		// If any KOMs are in the base list but not in the secondary list, add them to a new differential list
		for(StravaSegmentEffort baseKom : baseList) {
			if(!secondaryList.contains(baseKom)) {
				diffList.add(baseKom);
			}
		}
		
		return diffList;
	}

	public List<StravaSegmentEffort> getExistingKoms() {
		return existingKoms;
	}

	public void setExistingKoms(List<StravaSegmentEffort> existingKoms) {
		this.existingKoms = existingKoms;
	}

	public List<StravaSegmentEffort> getCurrentKoms() {
		return currentKoms;
	}

	public void setCurrentKoms(List<StravaSegmentEffort> currentKoms) {
		this.currentKoms = currentKoms;
	}

}
