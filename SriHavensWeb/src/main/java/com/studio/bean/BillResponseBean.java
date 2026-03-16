package com.studio.bean;

import java.util.SortedMap;

public class BillResponseBean {
	private SortedMap<String, BillBean> BILLMAP;
	private String BILLID;
	private boolean UPDATESTATUS;
	private boolean DELETESTATUS;
	private boolean DELIVERYSTATUS;
	private String MESSAGE;
	private boolean MESSAGESTATUS;
	private boolean CREATESTATUS;
	
	

	public boolean isCREATESTATUS() {
		return CREATESTATUS;
	}
	public void setCREATESTATUS(boolean cREATESTATUS) {
		CREATESTATUS = cREATESTATUS;
	}
	public void setBILLMAP(SortedMap<String, BillBean> bILLMAP) {
		BILLMAP = bILLMAP;
	}
	public void setBILLID(String bILLID) {
		BILLID = bILLID;
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
	public BillResponseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SortedMap<String, BillBean> getBILLMAP() {
		return BILLMAP;
	}
	public void setFRAMEMAP(SortedMap<String, BillBean> sortedMap) {
		BILLMAP = sortedMap;
	}
	public String getBILLID() {
		return BILLID;
	}
	public void setFRAMEID(String billID) {
		BILLID = billID;
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
	public boolean isDELIVERYSTATUS() {
		return DELIVERYSTATUS;
	}
	public void setDELIVERYSTATUS(boolean dELIVERYSTATUS) {
		DELIVERYSTATUS = dELIVERYSTATUS;
	}
	
	
}
