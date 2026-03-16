package com.studio.bean;

public class UserCreationStatusBean {
	boolean isUserCreated;
	String message;
	
	public UserCreationStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserCreationStatusBean(boolean isUserCreated, String message) {
		super();
		this.isUserCreated = isUserCreated;
		this.message = message;
	}
	public boolean IsUserCreated() {
		return isUserCreated;
	}
	public void setIsUserCreated(boolean isUserCreated) {
		this.isUserCreated = isUserCreated;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
