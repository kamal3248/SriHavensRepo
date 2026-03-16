package com.studio.bean;

public class MessageBean {
	public MessageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String message;
	private String status;
	private String redirectURL;
	
	public MessageBean(String message, String status, String redirectURL) {
		super();
		this.message = message;
		this.status = status;
		this.redirectURL = redirectURL;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
	
}
