package com.studio.bean;

public class CaptchaResponseBean {
	private String CAPTCHAIMAGESTRING;
	private boolean CAPTCHAMATCHING;
	
	public String getCAPTCHAIMAGESTRING() {
		return CAPTCHAIMAGESTRING;
	}
	public void setCAPTCHAIMAGESTRING(String cAPTCHAIMAGESTRING) {
		CAPTCHAIMAGESTRING = cAPTCHAIMAGESTRING;
	}
	public boolean isCAPTCHAMATCHING() {
		return CAPTCHAMATCHING;
	}
	public void setCAPTCHAMATCHING(boolean cAPTCHAMATCHING) {
		CAPTCHAMATCHING = cAPTCHAMATCHING;
	}
	
	
}
