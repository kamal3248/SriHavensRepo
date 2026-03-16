package com.studio.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FrameBean {
	private String FRAMEID;
	private String DESCRIPTION;
	private int QUANTITY;
	private BigDecimal PRICE;
	private String CREATEDBY;
	private Timestamp CREATEDON;
	private String UPDATEDBY;
	private Timestamp UPDATEDON;
	private String DELETEDBY;
	private Timestamp DELETEDON;
	private String STATUS;
	
	public FrameBean(String fRAMEID, String dESCRIPTION, int qUANTITY, BigDecimal pRICE, String cREATEDBY,
			Timestamp cREATEDON, String uPDATEDBY, Timestamp uPDATEDON, String dELETEDBY, Timestamp dELETEDON, String sTATUS) {
		super();
		FRAMEID = fRAMEID;
		DESCRIPTION = dESCRIPTION;
		QUANTITY = qUANTITY;
		PRICE = pRICE;
		CREATEDBY = cREATEDBY;
		CREATEDON = cREATEDON;
		UPDATEDBY = uPDATEDBY;
		UPDATEDON = uPDATEDON;
		DELETEDBY = dELETEDBY;
		DELETEDON = dELETEDON;
		STATUS=sTATUS;
	}
	public FrameBean() {
		super();
	}
	
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getFRAMEID() {
		return FRAMEID;
	}
	public void setFRAMEID(String fRAMEID) {
		FRAMEID = fRAMEID;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public int getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public BigDecimal getPRICE() {
		return PRICE;
	}
	public void setPRICE(BigDecimal pRICE) {
		PRICE = pRICE;
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
	
	
	
	
}
