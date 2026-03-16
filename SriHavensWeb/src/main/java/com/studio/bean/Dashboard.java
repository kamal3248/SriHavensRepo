package com.studio.bean;

public class Dashboard {
	
	private UserDetails userDetails;
	
	public Dashboard(UserDetails userDetails) {
		super();
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	
}
