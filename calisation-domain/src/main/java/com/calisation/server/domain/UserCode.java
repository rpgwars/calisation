package com.calisation.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Table
@Entity
public class UserCode {
	
	@Id
	@GeneratedValue
	private long id; 
	
	@Column
	private String userCode;
	
	@Column
	private String challange;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userCode", fetch = FetchType.LAZY, targetEntity = PositionNotification.class)
	private List<PositionNotification> positionList = new ArrayList<PositionNotification>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getChallange() {
		return challange;
	}

	public void setChallange(String challange) {
		this.challange = challange;
	}

	public List<PositionNotification> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PositionNotification> positionList) {
		this.positionList = positionList;
	}	
	
	public void addPositionNotification(PositionNotification positionNotification){
		positionList.add(positionNotification);
	}

}
