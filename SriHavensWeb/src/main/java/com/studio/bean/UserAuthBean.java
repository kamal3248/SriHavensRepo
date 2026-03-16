package com.studio.bean;

import java.sql.Timestamp;

public class UserAuthBean {

	boolean isUserAuthorized;
	boolean isPSWDDefault;
	String message;
	String redirectURL;
	String lastLoginAt;

	public UserAuthBean(boolean isUserAuthorized, boolean isPSWDDefault, String message, String redirectURL,
			String lastLoginAt) {
		super();
		this.isUserAuthorized = isUserAuthorized;
		this.isPSWDDefault = isPSWDDefault;
		this.message = message;
		this.redirectURL = redirectURL;
		this.lastLoginAt = lastLoginAt;
	}

	public UserAuthBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isUserAuthorized() {
		return isUserAuthorized;
	}

	public void setUserAuthorized(boolean isUserAuthorized) {
		this.isUserAuthorized = isUserAuthorized;
	}

	public boolean isPSWDDefault() {
		return isPSWDDefault;
	}

	public void setPSWDInitial(boolean isPSWDDefault) {
		this.isPSWDDefault = isPSWDDefault;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(String lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

}
