package com.studio.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BillBean {
	
	private String BILLID;
	private ArrayList<ItemBean> ITEMS = new ArrayList<>();
	private BigDecimal PRICE;
	private String CREATEDBY;
	private Timestamp CREATEDON;
	private String UPDATEDBY;
	private Timestamp UPDATEDON;
	private String DELETEDBY;
	private Timestamp DELETEDON;
	private String STATUS;
	private Timestamp DELIVERYDATE;
	private String DELIVERYSTATUS;
	
	
	public String getDELIVERYSTATUS() {
		return DELIVERYSTATUS;
	}
	public void setDELIVERYSTATUS(String dELIVERYSTATUS) {
		DELIVERYSTATUS = dELIVERYSTATUS;
	}
	public BillBean(String bILLID, ArrayList<ItemBean> iTEMS, BigDecimal pRICE, String cREATEDBY, Timestamp cREATEDON,
			String uPDATEDBY, Timestamp uPDATEDON, String dELETEDBY, Timestamp dELETEDON, String sTATUS,
			Timestamp dELIVERYDATE) {
		super();
		BILLID = bILLID;
		ITEMS = iTEMS;
		PRICE = pRICE;
		CREATEDBY = cREATEDBY;
		CREATEDON = cREATEDON;
		UPDATEDBY = uPDATEDBY;
		UPDATEDON = uPDATEDON;
		DELETEDBY = dELETEDBY;
		DELETEDON = dELETEDON;
		STATUS = sTATUS;
		DELIVERYDATE = dELIVERYDATE;
	}
	public BillBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBILLID() {
		return BILLID;
	}
	public void setBILLID(String bILLID) {
		BILLID = bILLID;
	}
	public ArrayList<ItemBean> getITEMS() {
		return ITEMS;
	}
	public void setITEMS(ArrayList<ItemBean> iTEMS) {
		ITEMS = iTEMS;
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
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public Timestamp getDELIVERYDATE() {
		return DELIVERYDATE;
	}
	public void setDELIVERYDATE(Timestamp dELIVERYDATE) {
		DELIVERYDATE = dELIVERYDATE;
	}
	
	
}
