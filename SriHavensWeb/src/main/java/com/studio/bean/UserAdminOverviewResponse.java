package com.studio.bean;

import java.util.HashMap;

public class UserAdminOverviewResponse {
	private HashMap<String, UserAdminOverview> USERMAP;
	private boolean UPDATESTATUS;
	private boolean DELETESTATUS;
	private String MESSAGE;
	private boolean MESSAGESTATUS;
	private boolean RECREATESTATUS;
	private boolean ISADMINUSER;
	private boolean CREATESTATUS;
	
	public boolean isCREATESTATUS() {
		return CREATESTATUS;
	}
	public void setCREATESTATUS(boolean cREATESTATUS) {
		CREATESTATUS = cREATESTATUS;
	}
	private HashMap<String, String> ROLEMAP;
	
	public HashMap<String, String> getROLEMAP() {
		return ROLEMAP;
	}
	public void setROLEMAP(HashMap<String, String> rOLEMAP) {
		ROLEMAP = rOLEMAP;
	}
	public HashMap<String, UserAdminOverview> getUSERMAP() {
		return USERMAP;
	}
	public void setUSERMAP(HashMap<String, UserAdminOverview> uSERMAP) {
		USERMAP = uSERMAP;
	}
	public boolean isUPDATESTATUS() {
		return UPDATESTATUS;
	}
	public void setUPDATESTATUS(boolean uPDATESTATUS) {
		UPDATESTATUS = uPDATESTATUS;
	}
	public boolean isDELETESTATUS() {
		return DELETESTATUS;
	}
	public void setDELETESTATUS(boolean dELETESTATUS) {
		DELETESTATUS = dELETESTATUS;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public boolean isMESSAGESTATUS() {
		return MESSAGESTATUS;
	}
	public void setMESSAGESTATUS(boolean mESSAGESTATUS) {
		MESSAGESTATUS = mESSAGESTATUS;
	}
	public boolean isRECREATESTATUS() {
		return RECREATESTATUS;
	}
	public void setRECREATESTATUS(boolean rECREATESTATUS) {
		RECREATESTATUS = rECREATESTATUS;
	}
	public boolean isISADMINUSER() {
		return ISADMINUSER;
	}
	public void setISADMINUSER(boolean iSADMINUSER) {
		ISADMINUSER = iSADMINUSER;
	}
	
	
	
	
}
