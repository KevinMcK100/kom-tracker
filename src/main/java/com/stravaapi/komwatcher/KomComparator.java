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
		
		return compareKoms(currentKoms, existingKoms);
	}
	
	public List<StravaSegmentEffort> getKomsGained() {
		
		return compareKoms(existingKoms, currentKoms);
	}
	
	/**
	 * Returns KOMs which are in the base list but not in the secondary list
	 */
	private List<StravaSegmentEffort> compareKoms(List<StravaSegmentEffort> baseList, List<StravaSegmentEffort> secondaryList) {
		
		List<StravaSegmentEffort> diffList = new ArrayList<StravaSegmentEffort>();
		
		// If any KOMs are in the base list but not in the secondary list, add them to a new differential list
		for(StravaSegmentEffort secondaryListItem : secondaryList) {
			boolean containsId = false;
			int secondaryItemId = secondaryListItem.getSegment().getId();
			for(StravaSegmentEffort baseListItem : baseList) {
				int baseListId = baseListItem.getSegment().getId();
				if(secondaryItemId == baseListId) {
					containsId = true;
					break;
				}
			}
			if(!containsId) {
				diffList.add(secondaryListItem);
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
