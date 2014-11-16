package com.calisation.server.domain.transfer.presentation;


public class PositionNotificationPresentation {

	private double latitude; 
	
	private double longtitude; 
	
	private String description; 
	
	public PositionNotificationPresentation(){
		
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}

