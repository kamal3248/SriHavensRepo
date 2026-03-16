package com.studio.bean;
import java.util.HashMap;
import java.util.SortedMap;

public class MirrorResponseBean {
	private SortedMap<String, MirrorBean> MIRRORMAP;
	private String MIRRORID;
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

	public MirrorResponseBean() {
		super();
		// TODO Auto-generated constructor stub
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

	public SortedMap<String, MirrorBean> getMIRRORMAP() {
		return MIRRORMAP;
	}
	public void setMIRRORMAP(SortedMap<String, MirrorBean> sortedMap) {
		MIRRORMAP = sortedMap;
	}
	public String getMIRRORID() {
		return MIRRORID;
	}
	public void setMIRRORID(String mIRRORID) {
		MIRRORID = mIRRORID;
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
