package com.studio.bean;

import java.util.HashMap;
import java.util.SortedMap;

public class PhotoMasterResponseBean {
	private SortedMap<String, PhotosBean> PHOTOSMAP;
	private String PHOTOID;
	private boolean UPDATESTATUS;
	private boolean DELETESTATUS;
	private String MESSAGE;
	private boolean MESSAGESTATUS;
	private boolean RECREATESTATUS;
	private boolean ISADMINUSER;
	
	
	public PhotoMasterResponseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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


	public SortedMap<String, PhotosBean> getPHOTOSMAP() {
		return PHOTOSMAP;
	}
	public void setPHOTOSMAP(SortedMap<String, PhotosBean> sortedMap) {
		PHOTOSMAP = sortedMap;
	}
	public String getPHOTOID() {
		return PHOTOID;
	}
	public void setPHOTOID(String pHOTOID) {
		PHOTOID = pHOTOID;
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
