package com.studio.bean;

import java.math.BigDecimal;

public class ItemBean {
	private String ITEMID;
	private String ITEMDESC;
	private BigDecimal ITEMPRICE;
	private int QUANTITY;
	
	public ItemBean(String iTEMID, String iTEMDESC, BigDecimal itemPrice, int qUANTITY) {
		super();
		this.ITEMID = iTEMID;
		this.ITEMDESC = iTEMDESC;
		this.ITEMPRICE = itemPrice;
		this.QUANTITY = qUANTITY;
	}

	public String getITEMID() {
		return ITEMID;
	}

	public void setITEMID(String iTEMID) {
		ITEMID = iTEMID;
	}

	public String getITEMDESC() {
		return ITEMDESC;
	}

	public void setITEMDESC(String iTEMDESC) {
		ITEMDESC = iTEMDESC;
	}

	public BigDecimal getITEMPRICE() {
		return ITEMPRICE;
	}

	public void setITEMPRICE(BigDecimal iTEMPRICE) {
		ITEMPRICE = iTEMPRICE;
	}

	public int getQUANTITY() {
		return QUANTITY;
	}

	public void setQUANTITY(int qUANTITY) {
		QUANTITY = qUANTITY;
	}
	
	
	
}
