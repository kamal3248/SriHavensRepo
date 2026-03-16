package com.studio.bean;

import java.sql.Timestamp;

public class UserDetails {
	
	private String UID;
	private String NAME;
	private String EMAIL;
	private String MOBILE;
	private String LASTLOGINAT;
	private String ROLE;
	private String PASSWORD;
	private String CREATEDBY;
	private Timestamp CREATEDON;
	private String UPDATEDBY;
	private Timestamp UPDATEDON;
	private String DELETEDBY;
	private Timestamp DELETEDON;
	private String STATUS;
	
	
	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserDetails(String uID, String nAME, String eMAIL, String mOBILE, String lASTLOGINAT, String rOLE,
			String pASSWORD) {
		super();
		UID = uID;
		NAME = nAME;
		EMAIL = eMAIL;
		MOBILE = mOBILE;
		LASTLOGINAT = lASTLOGINAT;
		ROLE = rOLE;
		PASSWORD = pASSWORD;
	}


	public String getUID() {
		return UID;
	}


	public void setUID(String uID) {
		UID = uID;
	}


	public String getNAME() {
		return NAME;
	}


	public void setNAME(String nAME) {
		NAME = nAME;
	}


	public String getEMAIL() {
		return EMAIL;
	}


	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}


	public String getMOBILE() {
		return MOBILE;
	}


	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}


	public String getLASTLOGINAT() {
		return LASTLOGINAT;
	}


	public void setLASTLOGINAT(String lASTLOGINAT) {
		LASTLOGINAT = lASTLOGINAT;
	}


	public String getROLE() {
		return ROLE;
	}


	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}


	public String getPASSWORD() {
		return PASSWORD;
	}


	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}


	public String getCREATEDBY() {
		return CREATEDBY;
	}


	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}


	public Timestamp getCREATEDON() {
		return CREATEDON;
	}


	public void setCREATEDON(Timestamp cREATEDON) {
		CREATEDON = cREATEDON;
	}


	public String getUPDATEDBY() {
		return UPDATEDBY;
	}


	public void setUPDATEDBY(String uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}


	public Timestamp getUPDATEDON() {
		return UPDATEDON;
	}


	public void setUPDATEDON(Timestamp uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}


	public String getDELETEDBY() {
		return DELETEDBY;
	}


	public void setDELETEDBY(String dELETEDBY) {
		DELETEDBY = dELETEDBY;
	}


	public Timestamp getDELETEDON() {
		return DELETEDON;
	}


	public void setDELETEDON(Timestamp dELETEDON) {
		DELETEDON = dELETEDON;
	}


	public String getSTATUS() {
		return STATUS;
	}


	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	
}
