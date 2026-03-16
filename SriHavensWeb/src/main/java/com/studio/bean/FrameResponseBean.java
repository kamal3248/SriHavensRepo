package com.studio.bean;

import java.util.HashMap;
import java.util.SortedMap;

public class FrameResponseBean {
	private SortedMap<String, FrameBean> FRAMEMAP;
	private String FRAMEID;
	private boolean UPDATESTATUS;
	private boolean DELETESTATUS;
	private String MESSAGE;
	private boolean MESSAGESTATUS;
	private boolean RECREATESTATUS;
	private boolean ISADMINUSER;
	
	
	
	public boolean isISADMINUSER() {
		return ISADMINUSER;
	}
	public void setISADMINUSER(boolean iSADMINUSER) {
		ISADMINUSER = iSADMINUSER;
	}
	public boolean getRECREATESTATUS() {
		return RECREATESTATUS;
	}
	public void setRECREATESTATUS(boolean rECREATESTATUS) {
		RECREATESTATUS = rECREATESTATUS;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public boolean getMESSAGESTATUS() {
		return MESSAGESTATUS;
	}
	public void setMESSAGESTATUS(boolean mESSAGESTATUS) {
		MESSAGESTATUS = mESSAGESTATUS;
	}
	public FrameResponseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SortedMap<String, FrameBean> getFRAMEMAP() {
		return FRAMEMAP;
	}
	public void setFRAMEMAP(SortedMap<String, FrameBean> sortedMap) {
		FRAMEMAP = sortedMap;
	}
	public String getFRAMEID() {
		return FRAMEID;
	}
	public void setFRAMEID(String fRAMEID) {
		FRAMEID = fRAMEID;
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
	
	
}
