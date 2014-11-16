package com.calisation.server.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;


@Table
@Entity
public class PositionNotification {
	
	@Id
	@GeneratedValue
	private long id; 
	
	@Column
	private double latitude;
	
	@Column
	private double longtitude;
	
	@Column
	private Date date; 
	
	@Column
	private String timeZone; 
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = UserCode.class)
	private UserCode userCode; 
	
	
	public PositionNotificationPresentation getPositionNotificationPresentation(){
	
		PositionNotificationPresentation presentation = new PositionNotificationPresentation();
		presentation.setLatitude(this.latitude);
		presentation.setLongtitude(this.longtitude);
		
		if(date == null)
			presentation.setDescription("");
		else{
		    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm a zzzz");   
		    TimeZone tz = TimeZone.getDefault();
		    if(timeZone != null)
		    	tz = TimeZone.getTimeZone(timeZone); 
		    sdf.setTimeZone(tz);
		    presentation.setDescription(sdf.format(date));
		}
		
		return presentation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public UserCode getUserCode() {
		return userCode;
	}

	public void setUserCode(UserCode userCode) {
		this.userCode = userCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	
	

}
