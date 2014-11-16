package com.calisation.server.domain.transfer.request;

public class UserCodeRequest {
	
	private String userCode; 
	
	private String challange;

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
	
	

}
