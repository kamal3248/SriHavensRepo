package com.studio.bean;

import java.sql.Timestamp;

public class UserAdminOverview {

	private String UID;
	private String NAME;
	private String EMAIL; 
	private String CONTACT;
	private Timestamp CREATEDON;
	private String CREATEDBY;
	private Timestamp UPDATEDON;
	private String UPDATEDBY;
	private Timestamp DELETEDON;
	private String DELETEDBY;
	private Timestamp LASTLOGINAT;
	private String ROLE;
	private String ACTIVE;
	private String ROLENAME;
	private String STATUS;
	private Timestamp PSWDCHANGEDON;
	private String PSWDCHANGEDBY;
	
	
	public UserAdminOverview(String uID, String nAME, String eMAIL, String cONTACT, Timestamp cREATEDON,
			String cREATEDBY, Timestamp uPDATEDON, String uPDATEDBY, Timestamp dELETEDON, String dELETEDBY,
			Timestamp lASTLOGINAT, String rOLE, String aCTIVE, String rOLENAME, String sTATUS, Timestamp pSWDCHANGEDON,
			String pSWDCHANGEDBY) {
		super();
		UID = uID;
		NAME = nAME;
		EMAIL = eMAIL;
		CONTACT = cONTACT;
		CREATEDON = cREATEDON;
		CREATEDBY = cREATEDBY;
		UPDATEDON = uPDATEDON;
		UPDATEDBY = uPDATEDBY;
		DELETEDON = dELETEDON;
		DELETEDBY = dELETEDBY;
		LASTLOGINAT = lASTLOGINAT;
		ROLE = rOLE;
		ACTIVE = aCTIVE;
		ROLENAME = rOLENAME;
		STATUS = sTATUS;
		PSWDCHANGEDON = pSWDCHANGEDON;
		PSWDCHANGEDBY = pSWDCHANGEDBY;
	}


	public String getSTATUS() {
		return STATUS;
	}


	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}


	public Timestamp getPSWDCHANGEDON() {
		return PSWDCHANGEDON;
	}


	public void setPSWDCHANGEDON(Timestamp pSWDCHANGEDON) {
		PSWDCHANGEDON = pSWDCHANGEDON;
	}


	public String getPSWDCHANGEDBY() {
		return PSWDCHANGEDBY;
	}


	public void setPSWDCHANGEDBY(String pSWDCHANGEDBY) {
		PSWDCHANGEDBY = pSWDCHANGEDBY;
	}


	public UserAdminOverview() {
		super();
	}
	


	public String getROLENAME() {
		return ROLENAME;
	}


	public void setROLENAME(String rOLENAME) {
		ROLENAME = rOLENAME;
	}


	public String getNAME() {
		return NAME;
	}


	public void setNAME(String nAME) {
		NAME = nAME;
	}


	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}

	public Timestamp getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Timestamp cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Timestamp getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Timestamp uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(String uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public Timestamp getDELETEDON() {
		return DELETEDON;
	}

	public void setDELETEDON(Timestamp dELETEDON) {
		DELETEDON = dELETEDON;
	}

	public String getDELETEDBY() {
		return DELETEDBY;
	}

	public void setDELETEDBY(String dELETEDBY) {
		DELETEDBY = dELETEDBY;
	}

	public Timestamp getLASTLOGINAT() {
		return LASTLOGINAT;
	}

	public void setLASTLOGINAT(Timestamp lASTLOGINAT) {
		LASTLOGINAT = lASTLOGINAT;
	}

	public String getROLE() {
		return ROLE;
	}

	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}

	public String getACTIVE() {
		return ACTIVE;
	}

	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}
	
	
	
}
